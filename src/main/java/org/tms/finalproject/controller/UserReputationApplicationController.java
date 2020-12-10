package org.tms.finalproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.tms.finalproject.dto.CommentDto;
import org.tms.finalproject.entity.Comment;
import org.tms.finalproject.repository.CommentRepository;
import org.tms.finalproject.service.database.CommentService;
import org.tms.finalproject.service.database.UserService;
import org.tms.finalproject.service.mapper.CommentDtoDoMapperService;

@Controller
@RequestMapping(path = "/reputation")
public class UserReputationApplicationController {

    public UserService userService;
    public CommentService commentService;
    public CommentDtoDoMapperService commentDtoDoMapperService;

    public UserReputationApplicationController(UserService userService,
                                               CommentService commentService,
                                               CommentDtoDoMapperService commentDtoDoMapperService) {
        this.userService = userService;
        this.commentService = commentService;
        this.commentDtoDoMapperService = commentDtoDoMapperService;
    }

    @PostMapping(path = "/set-comment")
    public ModelAndView getCommentForm(@RequestParam("order-id") long orderId,
                                       CommentDto commentDto,
                                       ModelAndView modelAndView) {
        Comment comment = commentDtoDoMapperService.buildDo(commentDto);
        commentService.createComment(comment);
        userService.recalculateAverageRatingForUserById(commentDto.getCommentOwnerId());
        String link = String.format("redirect:%s?order-id=%d", commentDto.getPagLink(), orderId);
        modelAndView.setViewName(link);
        return modelAndView;
    }
}
