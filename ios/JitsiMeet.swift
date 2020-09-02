


import JitsiMeet
import Foundation

@objc(JitsiMeet)
class JitsiMeet: RCTEventEmitter {
    
    @objc(call:userInfo:)
    func call(url: String, userInfoParams: [String: Any]) -> Void {
        
        DispatchQueue.main.async {
            let jitsiMeetViewController = JitsiMeetViewController()
            jitsiMeetViewController.delegate = self
            
            // Retrieve user info
            var userInfo = UserInfo()
            userInfo.displayName = userInfoParams["displayName"] as? String
            userInfo.email = userInfoParams["email"] as? String
            userInfo.avatar = userInfoParams["avatar"] as? String
            
            jitsiMeetViewController.call(url: url, userInfo: userInfo)
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

