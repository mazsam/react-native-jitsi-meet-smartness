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
