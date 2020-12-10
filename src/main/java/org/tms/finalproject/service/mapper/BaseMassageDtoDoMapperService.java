package org.tms.finalproject.service.mapper;

import org.springframework.stereotype.Service;
import org.tms.finalproject.dto.MassageDto;
import org.tms.finalproject.entity.dialog.Dialog;
import org.tms.finalproject.entity.Massage;
import org.tms.finalproject.entity.User;
import org.tms.finalproject.entity.dialog.embeddable.DialogId;
import org.tms.finalproject.repository.UserRepository;

@Service
public class BaseMassageDtoDoMapperService implements MassageDtoDoMapperService {

    private UserRepository userRepository;

    public BaseMassageDtoDoMapperService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Massage buildDo(MassageDto dto) {
        Massage massage = new Massage();

        User orderAuthor = userRepository.findById(dto.getOrderAuthorId()).orElseThrow(RuntimeException::new);
        User orderExecutor = userRepository.findById(dto.getOrderExecutorId()).orElseThrow(RuntimeException::new);
        User massageAuthor = userRepository.findById(dto.getMassageAuthorId()).orElseThrow(RuntimeException::new);
        DialogId dialogId = new DialogId(orderAuthor, orderExecutor);
        Dialog dialog = new Dialog(dialogId, null);

        massage.setAuthor(massageAuthor);
        massage.setDialog(dialog);
        massage.setText(dto.getText());

        return massage;
    }
}
