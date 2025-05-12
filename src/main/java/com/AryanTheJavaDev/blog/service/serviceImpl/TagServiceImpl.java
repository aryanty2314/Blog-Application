package com.AryanTheJavaDev.blog.service.serviceImpl;

import com.AryanTheJavaDev.blog.entities.Tag;
import com.AryanTheJavaDev.blog.repository.TagRepository;
import com.AryanTheJavaDev.blog.service.TagService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService
{

    private final TagRepository tagRepository;

    @Override
    public List<Tag> getAllTags() {
        return tagRepository.findAllWithPostCount();
    }

    @Transactional
    @Override
    public List<Tag> createTag(Set<String> tagNames) {
        List<Tag> existingTags = tagRepository.findByNameIn(tagNames);

        Set<String> existingTagNames = existingTags.stream()
                .map(Tag::getName)
                .collect(Collectors.toSet());

        List<Tag> newTags = tagNames.stream()
                .filter(name -> !existingTagNames.contains(name))
                .map(name -> Tag.builder()
                        .name(name)
                        .posts(new HashSet<>())
                        .build())
                .toList();

        List<Tag> savedTags = new ArrayList<>();
        if(!newTags.isEmpty()) {
            savedTags = tagRepository.saveAll(newTags);
        }

        savedTags.addAll(existingTags);

        return savedTags;
    }

    @Transactional
    @Override
    public void deleteTag(UUID id) {
        tagRepository.findById(id).ifPresent(tag -> {
        if (!tag.getPosts().isEmpty())
        {
            throw new IllegalArgumentException("Tag has no posts");
        }
        tagRepository.deleteById(id);
        });
    }

    @Override
    public Tag getTagById(UUID id) {
       return tagRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<Tag> getTagByIds(Set<UUID> ids)
    {
        List<Tag> foundTags = tagRepository.findAllById(ids);
        if (foundTags.size() != ids.size())
        {
            throw new EntityNotFoundException("Not all specified tag Ids exist");

        }

        return foundTags;
    }


}
