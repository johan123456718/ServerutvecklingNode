package com.node.repositories;

import com.node.objects.canvasImage;
import org.springframework.data.repository.CrudRepository;

public interface imageRepository extends CrudRepository<canvasImage, Integer> {

    Iterable<canvasImage> findByCreator(String uuid);

}
