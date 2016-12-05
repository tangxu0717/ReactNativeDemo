/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
  AppRegistry,
  View,
  Image,
  Text,
  ViewPagerAndroid,
} from 'react-native';

export default class App extends Component {

  render() {
    return (
      <View style={{flex:1, flexDirection:'column'}}>
        <ViewPagerAndroid style={{alignItems: 'center', height:180}} initialPage={0}>
             <View>
                <Image
                  style={{width: 360, height:180}}
                  source={require('./img/banner1.jpeg')}
                  />
             </View>
             <View>
                <Image
                  style={{width: 360, height:180}}
                  source={require('./img/banner2.png')}
                />
             </View>
             <View>
                <Image
                  style={{width: 360, height:180}}
                  source={require('./img/banner3.jpeg')}
                />
             </View>
        </ViewPagerAndroid>
        <View style={{flex:1, flexDirection:'row', alignItems:'flex-end'}}>
          <View style={{flex:1, flexDirection:'row', justifyContent: 'space-around', backgroundColor:'#353535', height:50}}>
            <View style={{justifyContent: 'center', alignItems: 'center'}}>
              <Image
                style={{width: 30, height: 30}}
                source={require('./img/icon_index_home_press.png')}
                />
              <Text style={{color:'#999999'}}>首页2</Text>
            </View>
            <View style={{justifyContent: 'center', alignItems: 'center'}}>
              <Image
                style={{width: 30, height: 30}}
                source={require('./img/icon_index_project_press.png')}
                />
                <Text style={{color:'#999999'}}>投资</Text>
            </View>
            <View style={{justifyContent: 'center', alignItems: 'center'}}>
              <Image
                style={{width: 30, height: 30}}
                source={require('./img/icon_index_more_press.png')}
                />
                <Text style={{color:'#999999'}}>发现</Text>
            </View>
            <View style={{justifyContent: 'center', alignItems: 'center'}}>
              <Image
                style={{width: 30, height: 30}}
                source={require('./img/icon_index_my_press.png')}
                />
                <Text style={{color:'#999999'}}>我的</Text>
            </View>
          </View>
        </View>
      </View>
    );
  }

}

AppRegistry.registerComponent('react-native-module', () => App);
