package net.chrisfey.githubjobs

import net.chrisfey.githubjobs.di.NetworkModule
import net.chrisfey.githubjobs.repository.networking.GithubJobHttpClient

class FakeNetworkModule(val fakeGithubJobHttpClient: GithubJobHttpClient) : NetworkModule() {

    override fun githubJobHttpClient(): GithubJobHttpClient = fakeGithubJobHttpClient

}