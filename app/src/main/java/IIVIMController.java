public interface IIVIMController {

    IVIMMemoryStructures ReadNewIVIMMessages();
    void rebootController();
    void disconnectFromServer();
}
