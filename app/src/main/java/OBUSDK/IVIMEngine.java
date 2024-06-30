package OBUSDK;

import java.util.ArrayList;

import OBUSDK.JsonData.IVIM;

public class IVIMEngine {

    // Event interfaces
    public interface Notify1 {
        void onEvent(Object sender, IVIMDataEventArgs e);
    }
    public interface Notify2 {
        void onEvent(Object sender, IVIMDataEventArgs e);
    }
    public interface Notify3 {
        void onEvent(Object sender, IVIMDataEventArgs e);
    }
    public interface Notify4 {
        void onEvent(Object sender, IVIMDataEventArgs e);
    }
    public interface Notify5 {
        void onEvent(Object sender, IVIMDataEventArgs e);
    }
    public interface Notify6 {
        void onEvent(Object sender, IVIMDataEventArgs e);
    }

    // Event listeners
    private Notify1 awarenessZoneEntered;
    private Notify2 awarenessZoneLeaved;
    private Notify3 detectionZoneEntered;
    private Notify4 detectionZoneLeaved;
    private Notify5 relevanceZoneEntered;
    private Notify6 relevanceZoneLeaved;

    // Class members
    private IVIMMemoryStructures memoryStructures;
    private IGeoOperator geoOperator;
    private GPSLocation currentGPSLocation;
    private IIVIMController ivimController;

    private boolean inAwarenessZone;
    private boolean inDetectionZone;
    private boolean inRelevanceZone;

    // Constructor
    public IVIMEngine() {
        this.memoryStructures = new IVIMMemoryStructures();
        this.geoOperator = new TriangleAreaOperator();
    }

    // Getters and Setters
    public GPSLocation getCurrentGPSLocation() {
        return currentGPSLocation;
    }

    public void setAwarenessZoneEntered(Notify1 awarenessZoneEntered) {
        this.awarenessZoneEntered = awarenessZoneEntered;
    }

    public void setAwarenessZoneLeaved(Notify2 awarenessZoneLeaved) {
        this.awarenessZoneLeaved = awarenessZoneLeaved;
    }

    public void setDetectionZoneEntered(Notify3 detectionZoneEntered) {
        this.detectionZoneEntered = detectionZoneEntered;
    }

    public void setDetectionZoneLeaved(Notify4 detectionZoneLeaved) {
        this.detectionZoneLeaved = detectionZoneLeaved;
    }

    public void setRelevanceZoneEntered(Notify5 relevanceZoneEntered) {
        this.relevanceZoneEntered = relevanceZoneEntered;
    }

    public void setRelevanceZoneLeaved(Notify6 relevanceZoneLeaved) {
        this.relevanceZoneLeaved = relevanceZoneLeaved;
    }

    // Methods
    public void run(IVIM ivim) {
        InternalIVIMMessage newInternalIVIMMessage = ivimController.readNewIVIMMessage(ivim);
        addNewIVIMessageToEngine(newInternalIVIMMessage);
    }

    /*public void stopIVIController() {
        ivimController.disconnectFromServer();
    }*/

    public void setIVIController(IIVIMController ivimController) {
        this.ivimController = ivimController;
    }

    public void processGPSLocation(GPSLocation gpsLocation) {
        boolean segmentInsideZone;
        this.currentGPSLocation = gpsLocation;
        BearingValidator bearingValidator = new BearingValidator();

        for (InternalIVIMMessage iviMessage : memoryStructures.getInternalIVIMessages()) {
            // Iterate awarenessZone
            inAwarenessZone = false;

            for (IVIZone iviZone : iviMessage.getAwarenessZones().getIVIZones()) {
                for (IVIMSegment segment : iviZone.getSegments()) {
                    segmentInsideZone = geoOperator.isInsideZone(gpsLocation, segment, segment.getSegmentWidth());
                    //segmentInsideZone = true;

                    if (segmentInsideZone) {
                        boolean validBearing = bearingValidator.isBearingValid(gpsLocation, segment);
                        if (validBearing) {
                            inAwarenessZone = true;
                            eventHandler(IVIZoneEnum.IVI_ZONE_AWARENESS, iviMessage, segmentInsideZone);
                        }
                    }
                }
            }

            if (!inAwarenessZone) {
                eventHandler(IVIZoneEnum.IVI_ZONE_AWARENESS, iviMessage, inAwarenessZone);
            }

            // Iterate detectionZone
            inDetectionZone = false;
            for (IVIZone iviZone : iviMessage.getDetectionZones().getIVIZones()) {
                for (IVIMSegment segment : iviZone.getSegments()) {
                    segmentInsideZone = geoOperator.isInsideZone(gpsLocation, segment, segment.getSegmentWidth());
                    //segmentInsideZone = true;

                    if (segmentInsideZone) {
                        boolean validBearing = bearingValidator.isBearingValid(gpsLocation, segment);
                        if (validBearing) {
                            inDetectionZone = true;
                            eventHandler(IVIZoneEnum.IVI_ZONE_DETECTION, iviMessage, segmentInsideZone);
                        }
                    }
                }
            }

            if (!inDetectionZone) {
                eventHandler(IVIZoneEnum.IVI_ZONE_DETECTION, iviMessage, inDetectionZone);
            }

            // Iterate relevanceZone
            inRelevanceZone = false;
            for (IVIZone iviZone : iviMessage.getRelevanceZones().getIVIZones()) {
                for (IVIMSegment segment : iviZone.getSegments()) {
                    segmentInsideZone = geoOperator.isInsideZone(gpsLocation, segment, segment.getSegmentWidth());
                    //segmentInsideZone = true;

                    if (segmentInsideZone) {
                        boolean validBearing = bearingValidator.isBearingValid(gpsLocation, segment);
                        if (validBearing) {
                            inRelevanceZone = true;
                            eventHandler(IVIZoneEnum.IVI_ZONE_RELEVANCE, iviMessage, segmentInsideZone);
                        }
                    }
                }
            }

            if (!inRelevanceZone) {
                eventHandler(IVIZoneEnum.IVI_ZONE_RELEVANCE, iviMessage, inRelevanceZone);
            }
        }
    }

    private boolean engineHasIVIMessage(InternalIVIMMessage newIviMessage) {
        for (InternalIVIMMessage iviMessage : memoryStructures.getInternalIVIMessages()) {
            if (iviMessage.getHeader().getStationId() == newIviMessage.getHeader().getStationId()
                    && iviMessage.getMandatory().getIviIdentificationNumber() == newIviMessage.getMandatory().getIviIdentificationNumber()) {
                return true;
            }
        }
        return false;
    }

    public void addNewIVIMessageToEngine(InternalIVIMMessage iviMessage) {
        if (!engineHasIVIMessage(iviMessage)) {
            memoryStructures.getInternalIVIMessages().add(iviMessage);
        }
    }

    private void copyNewIVIMessagesToEngine(IVIMMemoryStructures newIVIMMemoryStructures) {
        for (InternalIVIMMessage iviMessage : newIVIMMemoryStructures.getInternalIVIMessages()) {
            if (!engineHasIVIMessage(iviMessage)) {
                memoryStructures.getInternalIVIMessages().add(iviMessage);
            }
        }
    }

    private void eventHandler(IVIZoneEnum zone, InternalIVIMMessage internalIVIMMessage, boolean segmentInsideZone) {
        if (zone == IVIZoneEnum.IVI_ZONE_AWARENESS) {
            if (segmentInsideZone && internalIVIMMessage.getUIAwarenessZoneState() == IVIUIStateEnum.IVI_STATE_CURRENTLY_NOT_IN_ZONE) {
                internalIVIMMessage.setUIAwarenessZoneState(IVIUIStateEnum.IVI_STATE_CURRENTLY_IN_ZONE);
                IVIMDataEventArgs ivimEventArgs = new IVIMDataEventArgs(internalIVIMMessage, currentGPSLocation);
                if (awarenessZoneEntered != null) {
                    awarenessZoneEntered.onEvent(this, ivimEventArgs);
                }
            }
            if (!segmentInsideZone && internalIVIMMessage.getUIAwarenessZoneState() == IVIUIStateEnum.IVI_STATE_CURRENTLY_IN_ZONE) {
                internalIVIMMessage.setUIAwarenessZoneState(IVIUIStateEnum.IVI_STATE_CURRENTLY_NOT_IN_ZONE);
                IVIMDataEventArgs ivimEventArgs = new IVIMDataEventArgs(internalIVIMMessage, currentGPSLocation);
                if (awarenessZoneLeaved != null) {
                    awarenessZoneLeaved.onEvent(this, ivimEventArgs);
                }
            }
        }

        if (zone == IVIZoneEnum.IVI_ZONE_DETECTION) {
            if (segmentInsideZone && internalIVIMMessage.getUIDetectionZoneState() == IVIUIStateEnum.IVI_STATE_CURRENTLY_NOT_IN_ZONE) {
                internalIVIMMessage.setUIDetectionZoneState(IVIUIStateEnum.IVI_STATE_CURRENTLY_IN_ZONE);
                IVIMDataEventArgs ivimEventArgs = new IVIMDataEventArgs(internalIVIMMessage, currentGPSLocation);
                if (detectionZoneEntered != null) {
                    detectionZoneEntered.onEvent(this, ivimEventArgs);
                }
            }
            if (!segmentInsideZone && internalIVIMMessage.getUIDetectionZoneState() == IVIUIStateEnum.IVI_STATE_CURRENTLY_IN_ZONE) {
                internalIVIMMessage.setUIDetectionZoneState(IVIUIStateEnum.IVI_STATE_CURRENTLY_NOT_IN_ZONE);
                IVIMDataEventArgs ivimEventArgs = new IVIMDataEventArgs(internalIVIMMessage, currentGPSLocation);
                if (detectionZoneLeaved != null) {
                    detectionZoneLeaved.onEvent(this, ivimEventArgs);
                }
            }
        }

        if (zone == IVIZoneEnum.IVI_ZONE_RELEVANCE) {
            if (segmentInsideZone && internalIVIMMessage.getUIRelevanceZoneState() == IVIUIStateEnum.IVI_STATE_CURRENTLY_NOT_IN_ZONE) {
                internalIVIMMessage.setUIRelevanceZoneState(IVIUIStateEnum.IVI_STATE_CURRENTLY_IN_ZONE);
                IVIMDataEventArgs ivimEventArgs = new IVIMDataEventArgs(internalIVIMMessage, currentGPSLocation);
                if (relevanceZoneEntered != null) {
                    relevanceZoneEntered.onEvent(this, ivimEventArgs);
                }
            }
            if (!segmentInsideZone && internalIVIMMessage.getUIRelevanceZoneState() == IVIUIStateEnum.IVI_STATE_CURRENTLY_IN_ZONE) {
                internalIVIMMessage.setUIRelevanceZoneState(IVIUIStateEnum.IVI_STATE_CURRENTLY_NOT_IN_ZONE);
                IVIMDataEventArgs ivimEventArgs = new IVIMDataEventArgs(internalIVIMMessage, currentGPSLocation);
                if (relevanceZoneLeaved != null) {
                    relevanceZoneLeaved.onEvent(this, ivimEventArgs);
                }
            }
        }
    }

    public void clearInternalStructures() {
        memoryStructures.getInternalIVIMessages().clear();
    }

    public int messageCount() {
        return memoryStructures.getInternalIVIMessages().size();
    }

    public void clearInternalMessageById(long iviIdentificationNumber) {
        for (InternalIVIMMessage message : new ArrayList<>(memoryStructures.getInternalIVIMessages())) {
            if (message.getMandatory().getIviIdentificationNumber() == iviIdentificationNumber) {
                if (message.getUIAwarenessZoneState() == IVIUIStateEnum.IVI_STATE_CURRENTLY_IN_ZONE) {
                    eventHandler(IVIZoneEnum.IVI_ZONE_AWARENESS, message, false);
                }
                if (message.getUIDetectionZoneState() == IVIUIStateEnum.IVI_STATE_CURRENTLY_IN_ZONE) {
                    eventHandler(IVIZoneEnum.IVI_ZONE_DETECTION, message, false);
                }
                if (message.getUIRelevanceZoneState() == IVIUIStateEnum.IVI_STATE_CURRENTLY_IN_ZONE) {
                    eventHandler(IVIZoneEnum.IVI_ZONE_RELEVANCE, message, false);
                }
                memoryStructures.getInternalIVIMessages().remove(message);
                break;
            }
        }
    }
}
