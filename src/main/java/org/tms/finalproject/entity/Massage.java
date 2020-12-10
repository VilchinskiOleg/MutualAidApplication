package org.tms.finalproject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.tms.finalproject.entity.dialog.Dialog;

import javax.persistence.*;

@Entity
@Table(name = "massages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Massage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({@JoinColumn(name = "dialog_id_author_of_order"),
                    @JoinColumn(name = "dialog_id_executor_of_order")})
    private Dialog dialog;

    @OneToOne
    private User author;

    private String text;
    //    private LocalDateTime orderCreatedDateTime = LocalDateTime.now();
}
