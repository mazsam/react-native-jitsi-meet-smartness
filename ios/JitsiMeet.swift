


import JitsiMeet
import Foundation

@objc(JitsiMeet)
class JitsiMeet: RCTEventEmitter {
    
    @objc(join:userInfo:featureFlags:)
    func join(url: String, userInfoParams: [String: Any], featureFlags: [String: Bool] = [:]) -> Void {
        
        DispatchQueue.main.async {
            let jitsiMeetViewController = JitsiMeetViewController()
            jitsiMeetViewController.delegate = self
            
            // Retrieve user info
            var userInfo = UserInfo()
            userInfo.displayName = userInfoParams["displayName"] as? String
            userInfo.email = userInfoParams["email"] as? String
            
            jitsiMeetViewController.join(url: url, userInfo: userInfo, featureFlags : featureFlags)
        }
    }
    
    override func supportedEvents() -> [String]! {
      return ["onConferenceTerminated", "onConferenceJoined"]
    }
    
}

extension JitsiMeet: JitsiMeetViewDelegate {
    public func conferenceTerminated(_ data: [AnyHashable : Any]!) {
        self.sendEvent(withName: "onConferenceTerminated", body: nil)
    }
    public func conferenceJoined(_ data: [AnyHashable : Any]!) {
        self.sendEvent(withName: "onConferenceJoined", body: nil)
    }
}

