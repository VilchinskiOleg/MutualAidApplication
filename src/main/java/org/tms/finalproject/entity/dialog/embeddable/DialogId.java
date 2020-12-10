package org.tms.finalproject.entity.dialog.embeddable;

import lombok.*;
import org.tms.finalproject.entity.User;
import javax.persistence.Embeddable;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DialogId implements Serializable {

    @OneToOne
    private User authorOrder;
    @OneToOne
    private User executorOrder;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DialogId dialogId = (DialogId) o;
        return Objects.equals(authorOrder, dialogId.authorOrder) &&
                Objects.equals(executorOrder, dialogId.executorOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorOrder, executorOrder);
    }
}
