import java.util.List;
import java.util.ArrayList;

interface Notify {
    void notify(Object sender, IVIMDataEventArgs e);
}

enum iviUIStateEnum {
    iviStateCurrentlyInZone,
    iviStateCurrentlyNotInZone
}

enum iviZoneEnum {
    iviZoneAwareness,
    iviZoneDetection,
    iviZoneRelevance
}

class IVIMEngine {
    public interface Notify1 extends Notify {}
    public interface Notify2 extends Notify {}
    public interface Notify3 extends Notify {}
    public interface Notify4 extends Notify {}
    public interface Notify5 extends Notify {}
    public interface Notify6 extends Notify {}

    private Notify1 awarenessZoneEntered;
    private Notify2 awarenessZoneLeaved;
    private Notify3 detectionZoneEntered;
    private Notify4 detectionZoneLeaved;
    private Notify5 relevanceZoneEntered;
    private Notify6 relevanceZoneLeaved;

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
        IVIMMemoryStructures iVIMMemoryStructures = this.ivimController.ReadNewIVIMMessages();
    }

    public void stopIVIController() {
        this.ivimController.disconnectFromServer();
    }

    public void setIVIController(IIVIMController ivimController) {
        this.ivimController = ivimController;
    }

    public void processGPSLocation(GPSLocation gpsLocation) {
        boolean segmentInsideZone;
        BearingValidator bearingValidator = new BearingValidator();

        this.currentGPSLocation = gpsLocation;

        for (InternalIVIMMessage iviMessage : this.memoryStructures.getInternalIVIMessages()) {
            this.inAwarenessZone = false;

            for (IVIZone iviZone : iviMessage.getAwarenessZones().getIvIZones()) {
                for (IVIMSegment ivimSegment : iviZone.getSegments()) {
                    segmentInsideZone = this.geoOperator.isInsideZone(gpsLocation, ivimSegment, ivimSegment.getSegmentWidth());

                    if (segmentInsideZone) {
                        boolean validBearing = bearingValidator.isBearingValid(gpsLocation, ivimSegment);
                        if (validBearing) {
                            this.inAwarenessZone = true;
                            this.eventHandler(iviZoneEnum.iviZoneAwareness, iviMessage, segmentInsideZone);
                        }
                    }
                }
            }

            if (!this.inAwarenessZone) {
                this.eventHandler(iviZoneEnum.iviZoneAwareness, iviMessage, false);
            }

            // Similar loops for detectionZone and relevanceZone
        }
    }

    private boolean engineHasIVIMessage(InternalIVIMMessage newIviMessage) {
        for (InternalIVIMMessage iviMessage : this.memoryStructures.getInternalIVIMessages()) {
            if (iviMessage.getHeader().getStationId() == newIviMessage.getHeader().getStationId() && iviMessage.getMandatory().getIviIdentificationNumber() == newIviMessage.getMandatory().getIviIdentificationNumber()) {
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

    private void eventHandler(iviZoneEnum zone, InternalIVIMMessage internalIVIMMessage, boolean segmentInsideZone) {
        // Implementation of event handling
    }

    public void clearInternalStructures() {
        this.memoryStructures.getInternalIVIMessages().clear();
    }

    public int messageCount() {
        return this.memoryStructures.getInternalIVIMessages().size();
    }

    public void clearInternalMessageById(long iviIdentificationNumber) {
        List<InternalIVIMMessage> messagesToRemove = new ArrayList<>();
        for (InternalIVIMMessage message : this.memoryStructures.getInternalIVIMessages()) {
            if (message.getMandatory().getIviIdentificationNumber() == iviIdentificationNumber) {
                if (message.getUIAwarenessZoneState() == iviUIStateEnum.iviStateCurrentlyInZone) {
                    this.eventHandler(iviZoneEnum.iviZoneAwareness, message, false);
                }

                if (message.getUIDetectionZoneState() == iviUIStateEnum.iviStateCurrentlyInZone) {
                    this.eventHandler(iviZoneEnum.iviZoneDetection, message, false);
                }

                if (message.getUIRelevanceZoneState() == iviUIStateEnum.iviStateCurrentlyInZone) {
                    this.eventHandler(iviZoneEnum.iviZoneRelevance, message, false);
                }

                messagesToRemove.add(message);
                break;
            }
        }
        this.memoryStructures.getInternalIVIMessages().removeAll(messagesToRemove);
    }
}

