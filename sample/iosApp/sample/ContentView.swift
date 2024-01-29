//
//  ContentView.swift
//  sample
//
//  Created by German Arutyunov on 26.01.2024.
//

import SwiftUI

struct ContentView: View {
    @State private var path: Destination = .registration
    @State private var token: String = ""
    
    var body: some View {
        NavigationStack() {
            switch path {
            case .onboarding:
                OnboardingView(token: $token)
            default:
                RegistrationView(path: $path, token: $token)
            }
        }
    }
}

#Preview {
    ContentView()
}
