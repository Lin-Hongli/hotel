package com.lhl.hotelweb.provider;

import com.alibaba.fastjson.JSON;
import com.lhl.hotelweb.dto.GitHubAccessTokenDTO;
import com.lhl.hotelweb.dto.GitHubUser;
import com.lhl.hotelweb.exception.CustomizeErrorCode;
import com.lhl.hotelweb.exception.CustomizeException;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

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
            System.out.println("执行getAccessToken"+accessToken);
            return accessToken;
        } catch (Exception e) {
            //访问超时或其他情况抛异常
            throw new CustomizeException("GitHub授权失败！国外服务器网络可能较差，再登录试试？");
        }



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

            //若网络差等获取信息异常，即获取不到用户名，返回空
            System.out.println("gitHubUser.getName()"+gitHubUser.getName());
            if(gitHubUser.getName()==null){
                throw new CustomizeException("GitHub获取用户信息失败！国外服务器网络可能较差，再登录试试？");
            }

            return gitHubUser;
        } catch (IOException e) {
            System.out.println("执行getGitHubUser失败，抛异常");
            //请求API异常，跳转错误页面，提示登录失败,这里没有执行/callback请求
            throw new CustomizeException("GitHub登录失败！国外服务器网络可能较差，再登录试试？");
        }
    }
}
