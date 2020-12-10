package org.tms.finalproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.tms.finalproject.dto.MassageDto;
import org.tms.finalproject.entity.Massage;
import org.tms.finalproject.service.database.DialogService;
import org.tms.finalproject.service.database.MassageService;
import org.tms.finalproject.service.mapper.MassageDtoDoMapperService;

@Controller
@RequestMapping(path = "/massages-chat")
public class MassagesChatApplicationController {

    private MassageService massageService;
    private DialogService dialogService;
    private MassageDtoDoMapperService massageDtoDoMapperService;

    public MassagesChatApplicationController(MassageService massageService,
                                             DialogService dialogService,
                                             MassageDtoDoMapperService massageDtoDoMapperService) {
        this.massageService = massageService;
        this.dialogService = dialogService;
        this.massageDtoDoMapperService = massageDtoDoMapperService;
    }

    @PostMapping(path = "/create-massage")
    private ModelAndView createMassage(@RequestParam("order-id") long orderId,
                                       @RequestParam("page-link") String pageLink,
                                       MassageDto massageDto,
                                       ModelAndView modelAndView) {
        Massage massage = massageDtoDoMapperService.buildDo(massageDto);
        if (!dialogService.existByDialogId(massage.getDialog().getId())) {
            dialogService.createDialog(massage.getDialog());
        }
        massageService.createMassage(massage);
        String link = String.format("redirect:%s?order-id=%d", pageLink, orderId);
        modelAndView.setViewName(link);
        return modelAndView;
    }
}
