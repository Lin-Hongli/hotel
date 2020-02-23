package com.lhl.hotelweb.provider;

import com.alibaba.fastjson.JSON;
import com.lhl.hotelweb.dto.GitHubAccessTokenDTO;
import com.lhl.hotelweb.dto.GitHubUser;
import okhttp3.*;
import org.apache.el.stream.Stream;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

@Component
public class GitHubProvider {

    //调用github获取access_token的 API
    public String getAccessToken(GitHubAccessTokenDTO gitHubAccessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        //传入获取access_token要发送的json数据
        RequestBody body = RequestBody.create(JSON.toJSONString(gitHubAccessTokenDTO),mediaType);
        //构建post请求
        Request request = new Request.Builder()
                //获取access_token的接口，固定的，需要post传入json参数
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();

        //发送post请求获取到access_token
        try (Response response = client.newCall(request).execute()) {
            String accessToken = response.body().string();
            System.out.println(accessToken);
            //access_token=6410e9f583f5f58c3c2c0xxxxxxxxxxxxx&scope=user&token_type=bearer
            return accessToken;
        } catch (Exception e) {
            e.printStackTrace();
        }

        //如果抛异常或其他情况return null
        return null;
    }

    //调用github的access_token API发送Get请求获取用户信息
    public GitHubUser getGitHubUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();

        //构建get请求
        Request request = new Request.Builder()
                //通过access_token获取用户信息的接口，需要get传入access_token
                .url("https://api.github.com/user?"+accessToken)
                .build();
        //发送get请求获取到用户的json数据
        try (Response response = client.newCall(request).execute()) {
            String userJson = response.body().string();
            //解析json数据，封装用户信息
            GitHubUser gitHubUser = JSON.parseObject(userJson, GitHubUser.class);
            return gitHubUser;
        } catch (IOException e) {
        }

        //如果抛异常或其他情况return null
        return new GitHubUser();
    }
}
