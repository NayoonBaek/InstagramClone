package com.sparta.homework2.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.sparta.homework2.dto.ArticleResponseDto;
import com.sparta.homework2.dto.request.ContentRequestDto;
import com.sparta.homework2.model.Article;
import com.sparta.homework2.model.Like;
import com.sparta.homework2.model.Member;
import com.sparta.homework2.repository.ArticleRepository;
import com.sparta.homework2.repository.LikeRepository;
import com.sparta.homework2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;
    private final AmazonS3 amazonS3;
    private final AmazonS3Client amazonS3Client;
    private final LikeRepository likeRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public List<ArticleResponseDto> getArticles() throws RuntimeException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long authId = Long.parseLong(auth.getName());

        Member member = memberRepository.findById(authId)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));

        List<ArticleResponseDto> articlesDto = articleRepository.findAll()
                .stream().map((article) -> {
                    ArticleResponseDto articleDto = article.toDto();

                    // DTO에 좋아요 여부 설정
                    Optional<Like> likes = likeRepository.findByMemberAndArticle(member, article);
                    if (likes.isPresent()){
                        articleDto.setLike(true);
                    }else {
                        articleDto.setLike(false);
                    }

                    // DTO에 이미지 저장
                    String image = article.getImage();
                    String imgPath = amazonS3Client.getUrl(bucket, image).toString();
                    articleDto.setImage(imgPath);

                    // 좋아요 개수 계산 & 저장
                    List<Like> likeList = likeRepository.findAllByArticleId(article.getId());
                    int likeCount = likeList.size();
                    articleDto.setLikesSize(likeCount);

                    // DTO 반환
                    return articleDto;
                }).collect(Collectors.toList());

        return articlesDto;
    }

    public ArticleResponseDto getArticle(Long id) throws RuntimeException {
        Article article = articleRepository.findById(id).orElse(null);
        String image = article.getImage();

        // 이미지 불러오기
        String imgPath = amazonS3Client.getUrl(bucket, image).toString();

        // 객체에 이미지 경로 저장
        article.setImage(imgPath);

        // 좋아요 개수 계산
        List<Like> likeList = likeRepository.findAllByArticleId(id);
        int likeCount = likeList.size();
        
        // 객체를 DTO로 변환
        // DTO에 이미지 경로 저장
        ArticleResponseDto articleDto = article.toDto();
        
        // 좋아요 개수 저장
        articleDto.setLikesSize(likeCount);

        return articleDto;
    }

    @Transactional
    public Article createArticle(ContentRequestDto contentRequestDto, MultipartFile multipartFile) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long authId = Long.parseLong(auth.getName());

        Member member = memberRepository.findById(authId)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));

        String s3FileName = null;
        // String image = null;
        if(!multipartFile.isEmpty()) {
            s3FileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();

            ObjectMetadata objMeta = new ObjectMetadata();
            objMeta.setContentLength(multipartFile.getInputStream().available());

            amazonS3.putObject(bucket,s3FileName,multipartFile.getInputStream(),objMeta);
            // image = amazonS3.getUrl(bucket,s3FileName).toString();
        }

        // 요청받은 DTO 로 DB에 저장할 객체 만들기
        Article article = new Article(member.getUsername(), contentRequestDto, s3FileName);

        articleRepository.save(article);

        return article;
    }

    @Transactional
    public Long deleteArticle(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long authId = Long.parseLong(auth.getName());

        Member member = memberRepository.findById(authId)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));

        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("해당 글이 존재하지 않습니다."));

        if(!member.getUsername().equals(article.getAuthor())) {
            throw new RuntimeException("작성자만 삭제할 수 있습니다.");
        }

        articleRepository.deleteById(id);
        return id;
    }

    @Transactional
    public Article updateArticle(Long id,  ContentRequestDto contentRequestDto) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long authId = Long.parseLong(auth.getName());

        Member member = memberRepository.findById(authId)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));

        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("해당 글이 존재하지 않습니다."));

        if(!member.getUsername().equals(article.getAuthor())) {
           throw new RuntimeException("작성자만 수정할 수 있습니다.");
        }

        // 객체 수정
        article.update(member.getUsername(), contentRequestDto);

        articleRepository.save(article);

        return article;
    }
}
