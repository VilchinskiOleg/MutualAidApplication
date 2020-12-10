package org.tms.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tms.finalproject.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
