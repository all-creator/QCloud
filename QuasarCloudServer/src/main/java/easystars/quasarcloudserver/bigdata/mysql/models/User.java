package easystars.quasarcloudserver.bigdata.mysql.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @NotNull
    Long telegramId;

    @NotNull
    String license;

    @NotNull
    String keyGen;

    String launcherInfo;

    @ManyToMany
    @JoinTable(
            name = "user_by",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "client_id"))
    private Set<Client> clients = new HashSet<>();

    public void addClient(Client client) {
        clients.add(client);
        client.getUsers().add(this);
    }

    public void removeClient(Client client) {
        clients.remove(client);
        client.getUsers().remove(this);
    }
}
