package com.AryanTheJavaDev.blog.service;

import com.AryanTheJavaDev.blog.entities.Tag;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface TagService
{
List<Tag> getAllTags();
List<Tag> createTag(Set<String> tagNames);
    void deleteTag(UUID id);
    Tag getTagById(UUID id);

    List<Tag> getTagByIds(Set<UUID> ids);
}
