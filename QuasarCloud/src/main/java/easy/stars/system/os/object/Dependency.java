package easy.stars.system.os.object;

import easy.stars.server.Server;
import easy.stars.server.protocol.QCProtocol;

import java.net.MalformedURLException;
import java.net.URL;

public class Dependency extends QCProtocol {

    String name;

    public Dependency(Runnable process, String name) throws MalformedURLException {
        super(process, new URL(Server.MAIN_URL+"dependency/"+name),"POST");
        this.setDoInput(true);
        this.name = name;
    }

}
