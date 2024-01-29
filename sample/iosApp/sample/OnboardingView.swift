//
//  OnboardingView.swift
//  sample
//
//  Created by German Arutyunov on 29.01.2024.
//

import SwiftUI
import sdk

struct WebView: UIViewControllerRepresentable {
    @Binding var token: String
        
    func makeUIViewController(context: Context) -> some UIViewController {
         WebView_iosKt.WebViewController(
            config: ViewConfig(
                url: resourceString("WEB_URL"),
                token: token
            )
        )
    }
    
    func updateUIViewController(_ uiViewController: UIViewControllerType, context: Context) {
    }
}

struct OnboardingView: View {
    @Binding var token: String
    
    var body: some View {
        NavigationStack {
            WebView(token: $token)
                .navigationTitle("Onboarding")
        }
    }
}
