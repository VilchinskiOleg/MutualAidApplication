package org.tms.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tms.finalproject.entity.dialog.Dialog;
import org.tms.finalproject.entity.dialog.embeddable.DialogId;

public interface DialogRepository extends JpaRepository<Dialog, DialogId> {
    boolean existsById(DialogId dialogId);
}
