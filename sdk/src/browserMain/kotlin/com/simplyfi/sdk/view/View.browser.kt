package com.simplyfi.sdk.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.mohamedrejeb.ksoup.html.parser.KsoupHtmlHandler
import com.mohamedrejeb.ksoup.html.parser.KsoupHtmlParser
import io.ktor.client.HttpClient
import io.ktor.client.engine.js.Js
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.browser.localStorage
import org.jetbrains.compose.web.dom.Div
import org.w3c.dom.Window

external val window: dynamic

@Composable
actual fun View(
    config: Config,
    modifier: Modifier,
    onCreated: () -> Unit,
    onDispose: () -> Unit
) {
    val scripts = remember { mutableStateListOf<String>() }
    val styles = remember { mutableStateListOf<String>() }

    Div {
        Base {
            href((window as Window).location.pathname)
        }
        Script {
            src("https://unpkg.com/zone.js@0.11.4/bundles/zone.umd.js")
        }
        styles.forEach {
            Link {
                href(it)
                rel("stylesheet")
            }
        }
        Root()
        scripts.forEach {
            Script {
                src(it)
                defer()
            }
        }
    }

    LaunchedEffect(Unit) {
        localStorage.setItem(config.tokenKey, "\"${config.token}\"")
        window.VIEW_ID = config.viewId

        val client = HttpClient(Js)

        val response = client.get(config.url)
        var isBody = false
        var isHead = false

        val parser = KsoupHtmlParser(
            handler = KsoupHtmlHandler
                .Builder()
                .onOpenTag { name, attributes, _ ->
                    if (name == "head") {
                        isHead = true
                    }
                    if (name == "body") {
                        isBody = true
                        isHead = false
                    }

                    if (isHead && name == "link"
                        && attributes["rel"] == "stylesheet"
                    ) {
                        attributes["href"]?.let { styles.add(it) }
                    }

                    if (isBody && name == "script") {
                        attributes["src"]?.let { scripts.add(it) }
                    }
                }
                .build()
        )

        parser.write(response.bodyAsText())
        parser.end()
    }
}