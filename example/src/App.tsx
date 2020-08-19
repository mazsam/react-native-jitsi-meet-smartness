import * as React from 'react';
import { StyleSheet, View, Text, TouchableOpacity, TextInput } from 'react-native';
import JitsiMeet from 'ko-react-native-jitsi-meet';

export default function App() {
  const [url, setUrl] = React.useState('https://meet.jit.si/exemple')
  const [email, setEmail] = React.useState('john@ko.com')
  const [displayName, setDisplayName] = React.useState('John Doe')
  const call = React.useCallback(() => {
    JitsiMeet.call(url, { email, displayName })
  }, [])
  return (
    <View style={styles.container}>
      <TextInput style={styles.input} placeholder={"Url"} defaultValue={url} onChangeText={setUrl} />
      <TextInput style={styles.input} placeholder={"Email"} defaultValue={email} onChangeText={setEmail} />
      <TextInput style={styles.input} placeholder={"Display Name"} defaultValue={displayName} onChangeText={setDisplayName} />
      <TouchableOpacity onPress={call} style={{ padding: 20, backgroundColor: 'red' }}>
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
  input: {
    marginVertical: 10
  }
});
