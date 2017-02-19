package com.lostandspotted.api;

import com.lostandspotted.models.Comment;

public class CommentRequest {
    private Comment comment;

    public CommentRequest(Comment comment) {
        this.comment = comment;
    }
}
