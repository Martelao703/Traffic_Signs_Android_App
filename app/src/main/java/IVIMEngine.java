import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

interface AwarenessZoneEnteredListener {
    void onAwarenessZoneEntered(InternalIVIMMessage internalIVIMMessage, IVIMDataEventArgs ivimEventArgs);
}

interface AwarenessZoneLeavedListener {
    void onAwarenessZoneLeaved(InternalIVIMMessage internalIVIMMessage, IVIMDataEventArgs ivimEventArgs);
}

interface DetectionZoneEnteredListener {
    void onDetectionZoneEntered(InternalIVIMMessage internalIVIMMessage, IVIMDataEventArgs ivimEventArgs);
}

interface DetectionZoneLeavedListener {
    void onDetectionZoneLeaved(InternalIVIMMessage internalIVIMMessage, IVIMDataEventArgs ivimEventArgs);
}

interface RelevanceZoneEnteredListener {
    void onRelevanceZoneEntered(InternalIVIMMessage internalIVIMMessage, IVIMDataEventArgs ivimEventArgs);
}

interface RelevanceZoneLeavedListener {
    void onRelevanceZoneLeaved(InternalIVIMMessage internalIVIMMessage, IVIMDataEventArgs ivimEventArgs);
}

public class IVIMEngine {
    private AwarenessZoneEnteredListener awarenessZoneEnteredListener;
    private AwarenessZoneLeavedListener awarenessZoneLeavedListener;
    private DetectionZoneEnteredListener detectionZoneEnteredListener;
    private DetectionZoneLeavedListener detectionZoneLeavedListener;
    private RelevanceZoneEnteredListener relevanceZoneEnteredListener;
    private RelevanceZoneLeavedListener relevanceZoneLeavedListener;

    private IVIMMemoryStructures memoryStructures;
    private IGeoOperator geoOperator;
    private GPSLocation currentGPSLocation;

    private IIVIMController ivimController;

    private boolean inAwarenessZone;
    private boolean inDetectionZone;
    private boolean inRelevanceZone;

    public GPSLocation getCurrentGPSLocation() {
        return currentGPSLocation;
    }

    public IVIMEngine() {
        this.memoryStructures = new IVIMMemoryStructures();
        this.geoOperator = new TriangleAreaOperator();
    }

    public void run() {
        IVIMMemoryStructures iVIMMemoryStructures = ivimController.readNewIVIMMessages();
    }

    public void stopIVIController() {
        ivimController.disconnectFromServer();
    }

    public void setIVIController(IIVIMController ivimController) {
        this.ivimController = ivimController;
    }

    public void processGPSLocation(GPSLocation gpsLocation) {
        boolean segmentInsideZone;
        BearingValidator bearingValidator = new BearingValidator();

        this.currentGPSLocation = gpsLocation;

        for (InternalIVIMMessage iviMessage : memoryStructures.getInternalIVIMessages()) {
            // Iterate awarenessZone
            this.inAwarenessZone = false;

            for (IVIZone iviZone : iviMessage.getAwarenessZones().getIVIZones()) {
                for (IVIMSegment ivimSegment : iviZone.getSegments()) {
                    segmentInsideZone = geoOperator.isInsideZone(gpsLocation, ivimSegment, ivimSegment.getSegmentWidth());

                    if (segmentInsideZone) {
                        boolean validBearing = bearingValidator.isBearingValid(gpsLocation, ivimSegment);
                        if (validBearing) {
                            this.inAwarenessZone = true;
                            this.eventHandler(IVIZoneEnum.IVI_ZONE_AWARENESS, iviMessage, segmentInsideZone);
                        }
                    }
                }
            }

            if (!this.inAwarenessZone) {
                this.eventHandler(IVIZoneEnum.IVI_ZONE_AWARENESS, iviMessage, this.inAwarenessZone);
            }

            // Iterate detectionZone
            this.inDetectionZone = false;
            for (IVIZone iviZone : iviMessage.getDetectionZones().getIVIZones()) {
                for (IVIMSegment ivimSegment : iviZone.getSegments()) {
                    segmentInsideZone = geoOperator.isInsideZone(gpsLocation, ivimSegment, ivimSegment.getSegmentWidth());

                    if (segmentInsideZone) {
                        boolean validBearing = bearingValidator.isBearingValid(gpsLocation, ivimSegment);
                        if (validBearing) {
                            this.inDetectionZone = true;
                            this.eventHandler(IVIZoneEnum.IVI_ZONE_DETECTION, iviMessage, segmentInsideZone);
                        }
                    }
                }
            }

            if (!this.inDetectionZone) {
                this.eventHandler(IVIZoneEnum.IVI_ZONE_DETECTION, iviMessage, this.inDetectionZone);
            }

            // Iterate relevanceZone
            this.inRelevanceZone = false;
            for (IVIZone iviZone : iviMessage.getRelevanceZones().getIVIZones()) {
                for (IVIMSegment ivimSegment : iviZone.getSegments()) {
                    segmentInsideZone = geoOperator.isInsideZone(gpsLocation, ivimSegment, ivimSegment.getSegmentWidth());

                    if (segmentInsideZone) {
                        boolean validBearing = bearingValidator.isBearingValid(gpsLocation, ivimSegment);
                        if (validBearing) {
                            this.inRelevanceZone = true;
                            this.eventHandler(IVIZoneEnum.IVI_ZONE_RELEVANCE, iviMessage, segmentInsideZone);
                        }
                    }
                }
            }

            if (!this.inRelevanceZone) {
                this.eventHandler(IVIZoneEnum.IVI_ZONE_RELEVANCE, iviMessage, this.inRelevanceZone);
            }
        }
    }

    private boolean engineHasIVIMessage(InternalIVIMMessage newIviMessage) {
        for (InternalIVIMMessage iviMessage : memoryStructures.getInternalIVIMessages()) {
            if (iviMessage.getHeader().getStationId() == newIviMessage.getHeader().getStationId() &&
                    iviMessage.getMandatory().getIviIdentificationNumber() == newIviMessage.getMandatory().getIviIdentificationNumber()) {
                return true;
            }
        }
        return false;
    }

    public void addNewIVIMessageToEngine(InternalIVIMMessage iviMessage) {
        if (!this.engineHasIVIMessage(iviMessage)) {
            this.memoryStructures.getInternalIVIMessages().add(iviMessage);
        }
    }

    private void copyNewIVIMessagesToEngine(IVIMMemoryStructures iVIMMemoryStructures) {
        for (InternalIVIMMessage iviMessage : iVIMMemoryStructures.getInternalIVIMessages()) {
            if (!this.engineHasIVIMessage(iviMessage)) {
                this.memoryStructures.getInternalIVIMessages().add(iviMessage);
            }
        }
    }

    private void eventHandler(IVIZoneEnum zone, InternalIVIMMessage internalIVIMMessage, boolean segmentInsideZone) {
        if (zone == IVIZoneEnum.IVI_ZONE_AWARENESS) {
            if (segmentInsideZone && internalIVIMMessage.getUIAwarenessZoneState() == IVIUIStateEnum.IVI_STATE_CURRENTLY_NOT_IN_ZONE) {
                internalIVIMMessage.setUIAwarenessZoneState(IVIUIStateEnum.IVI_STATE_CURRENTLY_IN_ZONE);
                // build event parameters
                IVIMDataEventArgs ivimEventArgs = new IVIMDataEventArgs(internalIVIMMessage, this.currentGPSLocation);
                // raise event
                if (awarenessZoneEnteredListener != null) {
                    awarenessZoneEnteredListener.onAwarenessZoneEntered(internalIVIMMessage, ivimEventArgs);
                }
            }

            if (!segmentInsideZone && internalIVIMMessage.getUIAwarenessZoneState() == IVIUIStateEnum.IVI_STATE_CURRENTLY_IN_ZONE) {
                internalIVIMMessage.setUIAwarenessZoneState(IVIUIStateEnum.IVI_STATE_CURRENTLY_NOT_IN_ZONE);
                // build event parameters
                IVIMDataEventArgs ivimEventArgs = new IVIMDataEventArgs(internalIVIMMessage, this.currentGPSLocation);
                // raise event
                if (awarenessZoneLeavedListener != null) {
                    awarenessZoneLeavedListener.onAwarenessZoneLeaved(internalIVIMMessage, ivimEventArgs);
                }
            }
        }

        if (zone == IVIZoneEnum.IVI_ZONE_DETECTION) {
            if (segmentInsideZone && internalIVIMMessage.getUIDetectionZoneState() == IVIUIStateEnum.IVI_STATE_CURRENTLY_NOT_IN_ZONE) {
                internalIVIMMessage.setUIDetectionZoneState(IVIUIStateEnum.IVI_STATE_CURRENTLY_IN_ZONE);
                // build event parameters
                IVIMDataEventArgs ivimEventArgs = new IVIMDataEventArgs(internalIVIMMessage, this.currentGPSLocation);
                // raise event
                if (detectionZoneEnteredListener != null) {
                    detectionZoneEnteredListener.onDetectionZoneEntered(internalIVIMMessage, ivimEventArgs);
                }
            }

            if (!segmentInsideZone && internalIVIMMessage.getUIDetectionZoneState() == IVIUIStateEnum.IVI_STATE_CURRENTLY_IN_ZONE) {
                internalIVIMMessage.setUIDetectionZoneState(IVIUIStateEnum.IVI_STATE_CURRENTLY_NOT_IN_ZONE);
                // build event parameters
                IVIMDataEventArgs ivimEventArgs = new IVIMDataEventArgs(internalIVIMMessage, this.currentGPSLocation);
                // raise event
                if (detectionZoneLeavedListener != null) {
                    detectionZoneLeavedListener.onDetectionZoneLeaved(internalIVIMMessage, ivimEventArgs);
                }
            }
        }

        if (zone == IVIZoneEnum.IVI_ZONE_RELEVANCE) {
            if (segmentInsideZone && internalIVIMMessage.getUIRelevanceZoneState() == IVIUIStateEnum.IVI_STATE_CURRENTLY_NOT_IN_ZONE) {
                internalIVIMMessage.setUIRelevanceZoneState(IVIUIStateEnum.IVI_STATE_CURRENTLY_IN_ZONE);
                // build event parameters
                IVIMDataEventArgs ivimEventArgs = new IVIMDataEventArgs(internalIVIMMessage, this.currentGPSLocation);
                // raise event
                if (relevanceZoneEnteredListener != null) {
                    relevanceZoneEnteredListener.onRelevanceZoneEntered(internalIVIMMessage, ivimEventArgs);
                }
            }

            if (!segmentInsideZone && internalIVIMMessage.getUIRelevanceZoneState() == IVIUIStateEnum.IVI_STATE_CURRENTLY_IN_ZONE) {
                internalIVIMMessage.setUIRelevanceZoneState(IVIUIStateEnum.IVI_STATE_CURRENTLY_NOT_IN_ZONE);
                // build event parameters
                IVIMDataEventArgs ivimEventArgs = new IVIMDataEventArgs(internalIVIMMessage, this.currentGPSLocation);
                // raise event
                if (relevanceZoneLeavedListener != null) {
                    relevanceZoneLeavedListener.onRelevanceZoneLeaved(internalIVIMMessage, ivimEventArgs);
                }
            }
        }
    }

    public void clearInternalStructures() {
        this.memoryStructures.getInternalIVIMessages().clear();
    }

    public int messageCount() {
        return this.memoryStructures.getInternalIVIMessages().size();
    }

    public void clearInternalMessageById(long iviIdentificationNumber) {
        Iterator<InternalIVIMMessage> iterator = this.memoryStructures.getInternalIVIMessages().iterator();
        while (iterator.hasNext()) {
            InternalIVIMMessage message = iterator.next();
            if (message.getMandatory().getIviIdentificationNumber() == iviIdentificationNumber) {
                if (message.getUIAwarenessZoneState() ==IVIUIStateEnum.IVI_STATE_CURRENTLY_IN_ZONE) {
                    this.eventHandler(IVIZoneEnum.IVI_ZONE_AWARENESS, message, false);
                }
                if (message.getUIDetectionZoneState() ==IVIUIStateEnum.IVI_STATE_CURRENTLY_IN_ZONE) {
                    this.eventHandler(IVIZoneEnum.IVI_ZONE_DETECTION, message, false);
                }
                if (message.getUIRelevanceZoneState() == IVIUIStateEnum.IVI_STATE_CURRENTLY_IN_ZONE) {
                    this.eventHandler(IVIZoneEnum.IVI_ZONE_RELEVANCE, message, false);
                }
                iterator.remove();
                break;
            }
        }
    }
}