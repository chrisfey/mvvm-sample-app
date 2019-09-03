package net.chrisfey.githubjobs.fakes

import net.chrisfey.githubjobs.di.NetworkModule
import net.chrisfey.githubjobs.repository.networking.GithubJobHttpClient
import okhttp3.OkHttpClient

class FakeNetworkModule(private val fakeGithubJobHttpClient: GithubJobHttpClient) : NetworkModule() {

    override fun githubJobHttpClient(okhttpClient: OkHttpClient): GithubJobHttpClient = fakeGithubJobHttpClient

}