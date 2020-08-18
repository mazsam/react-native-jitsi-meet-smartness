//
//  JitsiMeetViewController.swift
//  ko-react-native-jitsi-meet
//
//  Created by Audie on 17/08/2020.
//

import UIKit
import JitsiMeet

class JitsiMeetViewController: UIViewController {
    
    /**
       The main contructor display a default modal full screen controller
        - parameters:
            -  modalPresentationStyle : a given modal presentation
     */
    init(modalPresentationStyle : UIModalPresentationStyle = .fullScreen  ) {
        super.init(nibName: nil, bundle: nil)
        self.modalPresentationStyle = modalPresentationStyle
    }
    required init?(coder: NSCoder) {
        fatalError("init(coder:) is not supported")
    }
    
    /**
        Display the jitsi meet view
        - parameters :
            - url : The url of the conference
     */
    func call(url: String){
        // create and configure jitsimeet view
        let jitsiMeetView  =  JitsiMeetView()
        jitsiMeetView.delegate = self
        let options = JitsiMeetConferenceOptions.fromBuilder { (builder) in
            builder.welcomePageEnabled = false
            builder.audioOnly = false
            builder.room = url
        }
        jitsiMeetView.join(options)
        self.view = jitsiMeetView

        // present the controller on top of main aplication
        UIApplication.shared.keyWindow?.rootViewController?.present(self, animated: true, completion: nil)
    }
    
    fileprivate func cleanUp() {
        if(self.view != nil) {
            dismiss(animated: true, completion: nil)
            self.view = nil
        }
    }
}


extension JitsiMeetViewController: JitsiMeetViewDelegate {
    public func conferenceTerminated(_ data: [AnyHashable : Any]!) {
        cleanUp()
    }
}
