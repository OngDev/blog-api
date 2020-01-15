package com.ongdev.blog.api.models.entities

import lombok.Data
import lombok.NoArgsConstructor
import java.util.UUID
import javax.persistence.Entity
import javax.persistence.Id

@Entity
@Data
@NoArgsConstructor
class Article(
    @Id var id: UUID? = null,
    var title: String = "",
    var description: String = ""
)
//Feel free to change this to work on model task
