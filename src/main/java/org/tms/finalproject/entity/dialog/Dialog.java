package org.tms.finalproject.entity.dialog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tms.finalproject.entity.Massage;
import org.tms.finalproject.entity.dialog.embeddable.DialogId;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "dialogs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dialog {
    @EmbeddedId
    private DialogId id;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "dialog")
    private List<Massage> massages;

    @Override
    public String toString() {
        return massages.stream()
                .map(massage -> String.format("%s\n@%s", massage.getText(), massage.getAuthor().getUserName()))
                .reduce((a, b) -> String.format("%s\n\n%s", a, b)).orElseThrow(RuntimeException::new);
    }
}
