package com.AryanTheJavaDev.blog.controller;


import com.AryanTheJavaDev.blog.entities.Tag;
import com.AryanTheJavaDev.blog.mappers.TagMapper;
import com.AryanTheJavaDev.blog.service.TagService;
import com.AryanTheJavaDev.blog.dto_s.TagDto;
import com.AryanTheJavaDev.blog.dto_s.CreateTagRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;
    private final TagMapper tagMapper;

    @GetMapping
    public ResponseEntity<List<TagDto>> getAllTags() {
        List<Tag> tags = tagService.getAllTags();
        if (tags.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<TagDto> tagRespons = tags.stream().map(tagMapper::toTagResponse).toList();
        return new ResponseEntity<>(tagRespons, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<List<TagDto>> createTag(@RequestBody CreateTagRequest createTagRequest)
    {
        List<Tag> savedTags = tagService.createTag(createTagRequest.getNames());
        List<TagDto> createTagRespons = savedTags.stream().map(tagMapper::toTagResponse).toList();
        return new ResponseEntity<>(createTagRespons, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable UUID id)
    {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}
