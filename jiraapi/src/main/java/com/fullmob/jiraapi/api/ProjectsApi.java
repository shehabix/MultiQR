package com.fullmob.jiraapi.api;

import com.fullmob.jiraapi.models.Project;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;

public interface ProjectsApi {

    @GET("project")
    Observable<Response<List<Project>>> getProjects();
}
