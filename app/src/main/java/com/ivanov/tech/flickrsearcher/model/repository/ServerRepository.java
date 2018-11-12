package com.ivanov.tech.flickrsearcher.model.repository;

/**
 * Created by Developer on 17.05.18.
 */

import com.ivanov.tech.flickrsearcher.model.entity.FlickrResponse;

import io.reactivex.Single;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServerRepository {

    //Search Method
    @POST("services/rest/?method=flickr.photos.search")
    Single<FlickrResponse> searchPhotos(@Query("api_key") String api_key,
                                        @Query("sort") String sort,
                                        @Query("content_type") String content_type,
                                        @Query("per_page") int per_page,
                                        @Query("page") int page,
                                        @Query("has_geo") int has_geo,
                                        @Query("media") String media,
                                        @Query("format") String format,
                                        @Query("nojsoncallback") String nojsoncallback,
                                        @Query("text") String text);
}