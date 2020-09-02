# ko-react-native-jitsi-meet

Jitsi for KO

## Installation

```sh
npm install ko-react-native-jitsi-meet
```

## Android Configuration

Add the `com.ko.jitsimeet.JitsiMeetActivity` in `android/app/src/main/AndroidManifest.xml` with `singleTask` launch mode.

```xml
<application
      ....
>
<activity
        android:launchMode="singleTask"
        android:name="com.ko.jitsimeet.JitsiMeetActivity"
/>
</application>

```

## Usage

```js

import * as React from 'react';
import { StyleSheet, View, Text, TouchableOpacity, TextInput } from 'react-native';
import JitsiMeet from 'ko-react-native-jitsi-meet';

export default function App() {
  const [url, setUrl] = React.useState('https://meet.jit.si/exemple')
  const call = React.useCallback(() => {
    JitsiMeet.call(url)
  }, [])
  return (
    <View style={styles.container}>
      <TextInput placeholder={url} defaultValue={url} onChangeText={setUrl} />
      <TouchableOpacity onPress={call}>
        <Text>Start Jitsi</Text>
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
});

```
Jitsi can be embedded in your applications in two ways:
- By calling Jitsi API methods 
- By using the `<RNJitsiMeetView>` view 

## Using Jitsi API

The start of a Jitsi call is done using the `call()` method. This method takes as first parameter the information of the calling user.

```ts
import JitsiMeet, { eventEmitter } from 'ko-react-native-jitsi-meet';

interface UserInfo {
  email: string;
  displayName: string;
}
JitsiMeet.call('https://meet.jit.si/exemple', { email : 'john@ko.com', displayName: 'John Doe' })
```

It is possible to listen to the events of the Jitsi call (`onConferenceJoined`, `onConferenceTerminated`) by subscribing to the corresponding events.

```js
import JitsiMeet, { eventEmitter } from 'ko-react-native-jitsi-meet';

React.useEffect(() => {
    const eventListener = eventEmitter.addListener('onConferenceTerminated', () => {
      console.log('Conference is over, see you soon !')
    })
    return () => eventListener.remove();
  }, [])
```


## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT
