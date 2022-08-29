package easy.stars.server.protocol;

import javafx.application.Platform;

public abstract class Process {

    final Runnable runnable;
    long theadID;

    protected Process(Runnable process, boolean fxUse) {
        if (fxUse) {
            this.runnable = () -> {
                sendOutput();
                getInput();
                Platform.runLater(process);
                postProcess();
            };
        } else  {
            this.runnable = () -> {
                sendOutput();
                getInput();
                process.run();
                postProcess();
            };
        }
    }

    protected abstract void preProcess();

    protected abstract void postProcess();

    protected abstract void sendOutput();

    protected abstract void getInput();

    public void startProcess(){
        preProcess();
        var t = new Thread(this.runnable);
        t.start();
        theadID = t.getId();
    }
}
