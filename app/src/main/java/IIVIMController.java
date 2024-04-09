public interface IIVIMController {
    IVIMMemoryStructures readNewIVIMMessages();
    void rebootController();
    void disconnectFromServer();
}
