package easy.stars.server.protocol;

public abstract class Process {

    final Runnable runnable;
    long theadID;

    protected Process(Runnable process) {
        this.runnable = () -> {
            process.run();
            postProcess();
        };
    }

    protected abstract void preProcess();

    protected abstract void postProcess();

    protected void startProcess(){
        preProcess();
        var t = new Thread(this.runnable);
        t.start();
        theadID = t.getId();
    }
}
