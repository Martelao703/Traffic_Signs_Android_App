public interface IIVIMController {
    IVIMMemoryStructures ReadNewIVIMMessages();
    void RebootController();
    void DisconnectFromServer();
}