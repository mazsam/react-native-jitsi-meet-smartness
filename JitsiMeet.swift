


@objc(JitsiMeet)
class JitsiMeet: NSObject {
    
    @objc(call:userInfo:)
    func call(url: String, userInfoParams: [String: Any]) -> Void {
        DispatchQueue.main.async {
            let jitsiMeetViewController = JitsiMeetViewController()
            
            // Retrieve user info
            var userInfo = UserInfo()
            userInfo.displayName = userInfoParams["displayName"] as? String
            userInfo.email = userInfoParams["email"] as? String
            userInfo.avatar = userInfoParams["avatar"] as? String
            
            jitsiMeetViewController.call(url: url, userInfo: userInfo)
        }
        
    }
    
}
