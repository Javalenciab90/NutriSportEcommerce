import SwiftUI
import GoogleSignIn
import Firebase
import FirebaseCore
import FirebaseMessaging
import ComposeApp

@main
struct iOSApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate
    
    var body: some Scene {
        WindowGroup {
            ContentView()
                .ignoresSafeArea()
                .onOpenURL(
                    perform: { url in
                        GIDSignIn.sharedInstance.handle(url)
                    }
                )
        }
    }
}

class AppDelegate: NSObject, UIApplicationDelegate {
    
    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions _launchOptions: [UIApplication.LaunchOptionsKey: Any]? = nil
    ) -> Bool {
        FirebaseApp.configure()
        NotifierManager.shared.initialize(configuration: NotificationPlatformConfigurationIos(
              showPushNotification: true,
              askNotificationPermissionOnStart: true,
              notificationSoundName: nil
            )
        )
        return false
    }
    

    func application(_ application: UIApplication, didRegisterForRemoteNotificationsWithDeviceToken deviceToken: Data) {
          Messaging.messaging().apnsToken = deviceToken
    }
}
