package org.tms.finalproject.service.database;

import org.springframework.stereotype.Service;
import org.tms.finalproject.entity.dialog.Dialog;
import org.tms.finalproject.entity.User;
import org.tms.finalproject.entity.dialog.embeddable.DialogId;
import org.tms.finalproject.repository.DialogRepository;

import java.util.Optional;

@Service
public class DialogServiceImpl implements DialogService {

    public DialogRepository dialogRepository;

    public DialogServiceImpl(DialogRepository dialogRepository) {
        this.dialogRepository = dialogRepository;
    }

    @Override
    public void createDialog(Dialog dialog) {
        if (dialog == null) {
            throw new IllegalArgumentException("Dialog is null!");
        }
        dialogRepository.save(dialog);
    }

    @Override
    public Dialog getDialogByDialogMembers(User author, User executor) {
        if (author == null) {
            throw new IllegalArgumentException("Author is null!");
        } else if (executor == null) {
            throw new IllegalArgumentException("Executor is null!");
        }
        DialogId dialogId = new DialogId(author, executor);
        Optional<Dialog> dialogOptional = dialogRepository.findById(dialogId);

        return dialogOptional.orElseThrow(RuntimeException::new);
    }

    @Override
    public boolean existByDialogMembers(User author, User executor) {
        if (author == null) {
            throw new IllegalArgumentException("Author is null!");
        } else if (executor == null) {
            throw new IllegalArgumentException("Executor is null!");
        }
        DialogId dialogId = new DialogId(author, executor);
        return dialogRepository.existsById(dialogId);
    }

    @Override
    public boolean existByDialogId(DialogId dialogId) {
        if (dialogId == null) {
            throw new IllegalArgumentException("DialogId is null!");
        }
        return dialogRepository.existsById(dialogId);
    }
}
