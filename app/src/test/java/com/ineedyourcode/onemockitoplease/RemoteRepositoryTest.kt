package com.ineedyourcode.onemockitoplease

import com.ineedyourcode.onemockitoplease.data.remote.GitHubApi
import com.ineedyourcode.onemockitoplease.data.remote.Repository
import com.ineedyourcode.onemockitoplease.data.remote.dto.UserMapper
import com.ineedyourcode.onemockitoplease.data.remote.dto.UserProfileDto
import com.ineedyourcode.onemockitoplease.domain.entity.UserProfile
import com.ineedyourcode.onemockitoplease.domain.usecase.GetUserCallback
import okhttp3.Request
import okio.Timeout
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteRepositoryTest {
    lateinit var repository: Repository
    private val remoteApi = mock(GitHubApi::class.java)
    private val userCallback = mock(GetUserCallback::class.java)
    private val mapper = UserMapper()

    @Before
    fun initRepository() {
        repository = Repository(remoteApi, mapper)
    }

    @Test
    fun getGitHubUser_methodCall_Test() {
        val retrofitCall = mock(Call::class.java) as Call<UserProfileDto>
        `when`(remoteApi.getGitHubUser(anyString())).thenReturn(retrofitCall)
        repository.getUser(anyString(), userCallback)
        verify(remoteApi, times(1)).getGitHubUser(anyString())
    }

    @Test
    fun getGitHubUser_responseBodyIsNull_Test() {

        val retrofitCallback = mock(Callback::class.java) as Callback<UserProfileDto>
        val retrofitResponse = mock(Response::class.java) as Response<UserProfileDto>
        val retrofitCall = object : CustomRetrofitCall {
            override fun enqueue(callback: Callback<UserProfileDto>) {
                retrofitCallback.onResponse(this, retrofitResponse)
            }
        }
        `when`(remoteApi.getGitHubUser(anyString())).thenReturn(retrofitCall)

        `when`(retrofitCallback.onResponse(retrofitCall, retrofitResponse)).then {
            retrofitResponse.body()
        }

        `when`(retrofitResponse.body()).thenReturn(null)

        repository.getUser(anyString(), userCallback)
        assertNull(retrofitResponse.body())
    }

    @Test
    fun retrofitCallback_onFailure_Test() {
        val retrofitCallback = mock(Callback::class.java) as Callback<UserProfileDto>
        val error = IllegalAccessError("Сервер недоступен")
        val retrofitCall = object : CustomRetrofitCall {
            override fun enqueue(callback: Callback<UserProfileDto>) {
                retrofitCallback.onFailure(this, error)
            }
        }

        `when`(remoteApi.getGitHubUser(anyString())).thenReturn(retrofitCall)

        `when`(retrofitCallback.onFailure(retrofitCall, error)).then {
            userCallback.onError(error)
        }

        repository.getUser(anyString(), userCallback)

        verify(userCallback, times(1)).onError(error)
    }

    @Test
    fun retrofitResponse_isNull_Test() {
        val retrofitResponse = mock(Response::class.java) as Response<UserProfileDto>
        val error = NullPointerException("Пользователь не существует")
        val retrofitCallback = object : CustomRetrofitCallback {
            override fun onResponse(
                call: Call<UserProfileDto>,
                response: Response<UserProfileDto>,
            ) {
                if (response.body() == null) {
                    userCallback.onError(error)
                }
            }
        }


        val retrofitCall = object : CustomRetrofitCall {
            override fun enqueue(callback: Callback<UserProfileDto>) {
                retrofitCallback.onResponse(this, retrofitResponse)
            }
        }

        `when`(remoteApi.getGitHubUser(anyString())).thenReturn(retrofitCall)

        `when`(retrofitResponse.body()).thenReturn(null)

        repository.getUser(anyString(), userCallback)

        verify(userCallback, times(1)).onError(error)
    }

    @Test
    fun retrofitResponse_isNotNull_Test() {
        val result = mock(UserProfile::class.java)
        val retrofitResponse = mock(Response::class.java) as Response<UserProfileDto>
        val retrofitCallback = object : CustomRetrofitCallback {
            override fun onResponse(
                call: Call<UserProfileDto>,
                response: Response<UserProfileDto>,
            ) {
                response.body()?.let {
                    userCallback.onSuccess(result)
                }
            }
        }

        val retrofitCall = object : CustomRetrofitCall {
            override fun enqueue(callback: Callback<UserProfileDto>) {
                retrofitCallback.onResponse(this, retrofitResponse)
            }
        }

        `when`(remoteApi.getGitHubUser(anyString())).thenReturn(retrofitCall)

        `when`(retrofitResponse.body()).thenReturn(mock(UserProfileDto::class.java))

        repository.getUser("login", userCallback)

        verify(userCallback, times(1)).onSuccess(result)
    }
}

interface CustomRetrofitCall : Call<UserProfileDto> {
    override fun enqueue(callback: Callback<UserProfileDto>) {
        TODO("Not yet implemented")
    }

    override fun clone(): Call<UserProfileDto> {
        TODO("Not yet implemented")
    }

    override fun execute(): Response<UserProfileDto> {
        TODO("Not yet implemented")
    }

    override fun isExecuted(): Boolean {
        TODO("Not yet implemented")
    }

    override fun cancel() {
        TODO("Not yet implemented")
    }

    override fun isCanceled(): Boolean {
        TODO("Not yet implemented")
    }

    override fun request(): Request {
        TODO("Not yet implemented")
    }

    override fun timeout(): Timeout {
        TODO("Not yet implemented")
    }
}

interface CustomRetrofitCallback : Callback<UserProfileDto> {
    override fun onResponse(
        call: Call<UserProfileDto>,
        response: Response<UserProfileDto>,
    ) {
        TODO("Not yet implemented")
    }

    override fun onFailure(call: Call<UserProfileDto>, t: Throwable) {
        TODO("Not yet implemented")
    }
}