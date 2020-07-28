# ko-react-native-jitsi-meet

Jitsi for KO

## Installation

```sh
npm install ko-react-native-jitsi-meet
```

## Usage

```js

export default function App() {

  const call = React.useCallback(() => {
    JitsiMeet.call()
  }, [])
  return (
    <View style={styles.container}>
      <TouchableOpacity onPress={call}>
        <Text>Start Jitsi</Text>
      </TouchableOpacity>
    </View>
  );
}
```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT
