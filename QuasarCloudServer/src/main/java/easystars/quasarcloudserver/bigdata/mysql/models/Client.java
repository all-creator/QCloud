package easystars.quasarcloudserver.bigdata.mysql.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @NotNull
    String hash;

    @NotNull
    String info;

    @NotNull
    String ip;

    @ManyToMany(mappedBy = "clients")
    private Set<User> users = new HashSet<>();
}
