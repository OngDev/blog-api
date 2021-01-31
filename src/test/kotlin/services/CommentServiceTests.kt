package services

import com.ongdev.blog.api.exceptions.EntityCreationFailedException
import com.ongdev.blog.api.exceptions.EntityDeletingFailedException
import com.ongdev.blog.api.exceptions.EntityNotFoundException
import com.ongdev.blog.api.exceptions.EntityUpdatingFailedException
import com.ongdev.blog.api.models.dtos.requests.ArticleCreationRequest
import com.ongdev.blog.api.models.dtos.requests.CommentCreationRequest
import com.ongdev.blog.api.models.dtos.requests.CommentUpdatingRequest
import com.ongdev.blog.api.models.entities.Article
import com.ongdev.blog.api.models.entities.Comment
import com.ongdev.blog.api.models.repositories.ArticleRepository
import com.ongdev.blog.api.models.repositories.CommentRepository
import com.ongdev.blog.api.models.toArticleEntity
import com.ongdev.blog.api.models.toCommentEntity
import com.ongdev.blog.api.services.impl.CommentServiceImpl
import com.ongdev.blog.api.services.interfaces.CommentService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import java.util.*

class CommentServiceTests {
    private val commentRepository: CommentRepository = mock(CommentRepository::class.java)
    private val articleRepository: ArticleRepository = mock(ArticleRepository::class.java)

    private var commentService: CommentService = CommentServiceImpl(commentRepository, articleRepository)

    private lateinit var mockArticleCreationRequest: ArticleCreationRequest
    private lateinit var mockArticle: Article
    private lateinit var mockOptionalArticle: Optional<Article>

    private lateinit var mockCommentCreationRequest: CommentCreationRequest
    private lateinit var mockCommentUpdatingRequest: CommentUpdatingRequest
    private lateinit var mockComment: Comment
    private lateinit var mockOptionalComment: Optional<Comment>
    private lateinit var mockPageComments: PageImpl<Comment>

    @BeforeEach
    internal fun setUp() {
        mockArticleCreationRequest = ArticleCreationRequest(
                "Test title",
                "Test description",
                "Test content"
        )
        mockArticle = mockArticleCreationRequest.toArticleEntity()
        mockArticle.id = UUID.randomUUID()
        mockOptionalArticle = Optional.of(mockArticle)

        mockCommentCreationRequest = CommentCreationRequest(
                UUID.randomUUID(),
                "Test content",
                UUID.randomUUID(),
                null
        )
        mockCommentUpdatingRequest = CommentUpdatingRequest(
                "Test updated content"
        )
        mockComment = mockCommentCreationRequest.toCommentEntity()
        mockComment.id = UUID.randomUUID()
        mockOptionalComment = Optional.of(mockComment)
        val totalElement = ArrayList<Comment>()
        for (i in 1..10) {
            totalElement.add(mockComment)
        }
        mockPageComments = PageImpl(totalElement)
    }

    @Test
    fun `Create comment, should throw error when could not find article`() {
        `when`(articleRepository.findById(any(UUID::class.java))).thenThrow(EntityNotFoundException::class.java)

        assertThrows<EntityNotFoundException> { commentService.createComment(mockCommentCreationRequest) }
    }

    @Test
    fun `Create comment, should return comment`() {
        `when`(articleRepository.findById(any(UUID::class.java))).thenReturn(mockOptionalArticle)
        `when`(commentRepository.save(any(Comment::class.java)))
                .thenReturn(mockComment)

        val result = commentService.createComment(mockCommentCreationRequest)

        assertThat(result.commentId).isEqualTo(mockComment.id.toString())
    }

    @Test
    fun `Create comment, should throw error when could not save comment`() {
        `when`(articleRepository.findById(any(UUID::class.java))).thenReturn(mockOptionalArticle)
        `when`(commentRepository.save(any(Comment::class.java))).thenThrow(IllegalArgumentException())

        assertThrows<EntityCreationFailedException> { commentService.createComment(mockCommentCreationRequest) }
    }

    @Test
    fun `Update comment by comment id, should return updated comment`() {
        `when`(commentRepository.findById(any(UUID::class.java))).thenReturn(mockOptionalComment)
        `when`(commentRepository.save(any(Comment::class.java))).thenReturn(mockComment)

        val result = commentService.updateCommentById(UUID.randomUUID().toString(), mockCommentUpdatingRequest)

        assertThat(result.commentId).isEqualTo(mockComment.id.toString())
    }

    @Test
    fun `Update comment by comment id, should throw error when could not find comment`() {
        val uuid = any(UUID::class.java)
        `when`(commentRepository.findById(uuid)).thenThrow(EntityNotFoundException::class.java)

        assertThrows<EntityNotFoundException> { commentService.updateCommentById(UUID.randomUUID().toString(), mockCommentUpdatingRequest) }
    }

    @Test
    fun `Update comment by comment id, should throw error when could not save comment`() {
        `when`(commentRepository.findById(any(UUID::class.java))).thenReturn(mockOptionalComment)
        `when`(commentRepository.save(any(Comment::class.java))).thenThrow(IllegalArgumentException())

        assertThrows<EntityUpdatingFailedException> { commentService.updateCommentById(UUID.randomUUID().toString(), mockCommentUpdatingRequest) }
    }

    @Test
    fun `Get comment by comment id, should return comment`() {
        `when`(commentRepository.findById(any(UUID::class.java))).thenReturn(mockOptionalComment)

        val result = commentService.getCommentById(UUID.randomUUID().toString())

        assertThat(result.commentId).isEqualTo(mockComment.id.toString())
    }

    @Test
    fun `Get comment by comment id, should throw error when could not find comment`() {
        val uuid = any(UUID::class.java)
        `when`(commentRepository.findById(uuid)).thenThrow(EntityNotFoundException::class.java)

        assertThrows<EntityNotFoundException> { commentService.getCommentById(UUID.randomUUID().toString()) }
    }

    @Test
    fun `Delete comment by comment id, should throw error when could not find comment`() {
        val uuid = any(UUID::class.java)
        `when`(commentRepository.findById(uuid)).thenThrow(EntityNotFoundException::class.java)

        assertThrows<EntityNotFoundException> { commentService.deleteCommentById(UUID.randomUUID().toString()) }
    }

    @Test
    fun `Delete comment by comment id, should throw error when could not delete comment`() {
        `when`(commentRepository.findById(any(UUID::class.java))).thenReturn(mockOptionalComment)
        `when`(commentRepository.delete(any(Comment::class.java))).thenThrow(IllegalArgumentException())

        assertThrows<EntityDeletingFailedException> { commentService.deleteCommentById(UUID.randomUUID().toString()) }
    }

    @Test
    fun `Get comments by article, should return comments`() {
        val uuid = any(UUID::class.java)
        val page = PageRequest.of(0, 10)
        `when`(articleRepository.findById(uuid)).thenReturn(mockOptionalArticle)
        `when`(commentRepository.findAllByArticle(mockArticle, page)).thenReturn(mockPageComments)

        val result = commentService.getCommentsByArticle(UUID.randomUUID().toString(), page)

        assertThat(result.result.totalElements).isEqualTo(10)
    }

    @Test
    fun `Get comments by article, should throw error when could not find article`() {
        val uuid = any(UUID::class.java)
        val page = PageRequest.of(0, 10)
        `when`(articleRepository.findById(uuid)).thenThrow(EntityNotFoundException::class.java)

        assertThrows<EntityNotFoundException> { commentService.getCommentsByArticle(UUID.randomUUID().toString(), page) }
    }
}