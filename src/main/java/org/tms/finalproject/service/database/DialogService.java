package org.tms.finalproject.service.database;

import org.tms.finalproject.entity.dialog.Dialog;
import org.tms.finalproject.entity.User;
import org.tms.finalproject.entity.dialog.embeddable.DialogId;

public interface DialogService {
    void createDialog(Dialog dialog);
    Dialog getDialogByDialogMembers(User author, User executor);
    boolean existByDialogMembers(User author, User executor);
    boolean existByDialogId(DialogId dialogId);
}
