@objc(JitsiMeet)
class JitsiMeet: NSObject {


    @objc(call:)
    func call(url: String) -> Void {
        DispatchQueue.main.async {
            let jitsiMeetViewController = JitsiMeetViewController()
            jitsiMeetViewController.call(url: url)
        }
        
    }
    
}
