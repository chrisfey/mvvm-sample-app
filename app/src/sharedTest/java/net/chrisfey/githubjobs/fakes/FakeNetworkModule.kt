package net.chrisfey.githubjobs.fakes

import net.chrisfey.githubjobs.di.NetworkModule
import net.chrisfey.githubjobs.repository.networking.GithubJobHttpClient

class FakeNetworkModule(val fakeGithubJobHttpClient: GithubJobHttpClient) : NetworkModule() {

    override fun githubJobHttpClient(): GithubJobHttpClient = fakeGithubJobHttpClient

}