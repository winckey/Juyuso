/* eslint-disable no-undef */
import { OpenVidu } from 'openvidu-browser'
import axios from 'axios'
axios.defaults.headers.post['Content-Type'] = 'application/json';
import router from '../../router'
import Vue from 'vue';

const openviduStore = {
  namespaced: true,
  state: {
    Chat_OV: undefined,
    Chat_session: undefined,
    Chat_messages: [],
    OV: undefined,
    session: undefined,
    mainStreamManager: undefined,
    wholeSubscribers: [],
    publisher: undefined,
    subscribers: [],
    messages: [],
    gameMode: undefined,
    gameInfo: undefined,
    changeVoice: undefined
  },
  mutations: {
    SET_SESSION_INFO(state, data) {
      state.OV = data.OV
      state.session = data.session
      state.mainStreamManager = data.mainStreamManager
      state.publisher = data.publisher
      state.subscribers = data.subscribers
      state.messages = data.messages
    },
    SET_WHOLE_SESSION_INFO(state, data) {
      state.Chat_OV = data.OV
      state.Chat_session = data.session
      state.Chat_messages = data.messages
    },
    LEAVE_WHOLE_SESSION(state) {
      if (state.Chat_session) state.Chat_session.disconnect();
      state.Chat_OV = undefined
      state.Chat_session = undefined
      state.Chat_messages = []
    },
    LEAVE_SESSION(state) {
      if (state.session) state.session.disconnect();
			state.session = undefined;
			state.mainStreamManager = undefined;
			state.publisher = undefined;
			state.subscribers = [];
      state.wholeSubscribers = [];
      state.messages = [];
			state.OV = undefined;
    
      state.gameMode = undefined;
      state.gameInfo = undefined;
    },
    SET_WHOLE_SUBSCRIBERS(state, data) {
      state.wholeSubscribers = data.wholeSubscribers
    },
    SET_MESSAGE(state, data) {
      state.messages = data.messages
    },
    SET_WHOLE_MESSAGE(state, data) {
      state.Chat_messages = data.messages
    },
    SET_GAME_INFO(state, data) {
      state.gameInfo = data.gameInfo
    },
    SET_GAME_MODE(state, data) {
      state.gameMode = data.gameMode
    },
    SET_CHANGE_VOICE(state, data){
      state.changeVoice = data.changeVoice
    }
  },
  actions: {
    initSession: function ({ state, dispatch, commit }, userInfo) {
      if (state.Chat_OV != undefined) {
        return
      }
      let data = {
        OV: undefined,
        session: undefined,
        messages: []
      }
      data.OV = new OpenVidu()
      data.OV.enableProdMode()

      data.session = data.OV.initSession()
      
      data.session.on('signal:whole-chat', (event) => {
        data.messages.push(event)
        commit('SET_WHOLE_MESSAGE', data)
      });
      
      data.session.on('exception', ({ exception }) => {
				console.warn(exception);
			});
      dispatch('getToken', '0').then(token => {
        data.session.connect(token, { clientData: userInfo.nickname })
        commit('SET_WHOLE_SESSION_INFO', data)
      })

    },
    joinSession: function ({ dispatch, commit }, roomInfo) {
      let isLogin = localStorage.getItem('jwt') ? true : false
      if (!isLogin) {
        router.push({ name: 'Login' })
        return
      }
      let data = {
        OV: undefined,
        session: undefined,
        mainStreamManager: undefined,
        publisher: undefined,
        subscribers: [],
        messages: [],
        wholeSubscribers: [],
        gameMode: undefined,
        gameInfo: undefined,
      }
      const sessionId = roomInfo.sessionId

      data.OV = new OpenVidu()
      data.OV.enableProdMode()
			// --- Init a session ---
			data.session = data.OV.initSession();

			// --- Specify the actions when events take place in the session ---
			// On every new Stream received...
			data.session.on('streamCreated', ({ stream }) => {
        const subscriber = data.session.subscribe(stream);
				data.subscribers.push(subscriber);
        data.wholeSubscribers.push(subscriber);
        commit('SET_WHOLE_SUBSCRIBERS', data)
        if (data.publisher) {
          Vue.$toast.open({
            position: 'bottom',
            message: `${JSON.parse(subscriber.stream.connection.data).clientData}님이 입장하셨습니다.`,
            type: 'default',
            duration: 2500,
          })
        }
			});
			
      // On every Stream destroyed...
			data.session.on('streamDestroyed', ({ stream }) => {
        const index = data.subscribers.indexOf(stream.streamManager, 0);
        const whole_index = data.wholeSubscribers.indexOf(stream.streamManager, 0);
        Vue.$toast.open({
          position: 'bottom',
          message: `${JSON.parse(data.subscribers[index].stream.connection.data).clientData}님이 나가셨습니다.`,
          type: 'default',
          duration: 2500,
        })
				if (index >= 0) {
          data.subscribers.splice(index, 1);
				}
        if (whole_index >= 0) {
          data.wholeSubscribers.splice(whole_index, 1);
        }
        commit('SET_WHOLE_SUBSCRIBER', data)
			});

      data.session.on('signal:my-chat', (event) => {
        data.messages.push(event)
        commit('SET_MESSAGE', data)
      });
      
      data.session.on('signal:game-info', event => {
        data.gameInfo = event.data
        commit('SET_GAME_INFO', data)
      })

      data.session.on('signal:game-mode', event => {
        data.gameMode = event.data
        commit('SET_GAME_MODE', data)
      })

      data.session.on('signal:change-voice', event =>{
        data.changeVoice = event.data
        commit('SET_CHANGE_VOICE',data)
      })

			// On every asynchronous exception...
			data.session.on('exception', ({ exception }) => {
				console.warn(exception);
			});

			// --- Connect to the session with a valid user token ---
			// 'getToken' method is simulating what your server-side should do.
			// 'token' parameter should be retrieved and returned by your own backend
      if (roomInfo.isCreate) {
        dispatch('getToken', sessionId).then(token => {
          data.session.connect(token, { clientData: roomInfo.userName })
            .then(() => {
              // --- Get your own camera stream with the desired properties ---
              let publisher = data.OV.initPublisher(undefined, roomInfo.publishInfo);
              data.mainStreamManager = publisher;
              data.publisher = publisher;
              // --- Publish your stream ---
              // publisher.subscribeToRemote()
              data.session.publish(publisher);
              data.wholeSubscribers.push(publisher)
              dispatch('enterRoom', sessionId)
              commit('SET_SESSION_INFO', data)
              commit('SET_WHOLE_SUBSCRIBERS', data)
            })
            .catch(error => {
              console.log('There was an error connecting to the session:', error.code, error.message);
            });
        });
      }
      else {
        dispatch('createToken', sessionId).then(token => {
          data.session.connect(token, { clientData: roomInfo.userName })
            .then(() => {
              // --- Get your own camera stream with the desired properties ---
              let publisher = data.OV.initPublisher(undefined, roomInfo.publishInfo);
              data.mainStreamManager = publisher;
              data.publisher = publisher;
              // --- Publish your stream ---
              data.session.publish(publisher);
              data.wholeSubscribers.push(publisher)
              dispatch('enterRoom', sessionId)
              commit('SET_SESSION_INFO', data)
              commit('SET_WHOLE_SUBSCRIBERS', data)
            })
            .catch(error => {
              console.log('There was an error connecting to the session:', error.code, error.message);
            });
        });
      }
    },

    leaveSession ({ dispatch, commit }, data) {
			// --- Leave the session by calling 'disconnect' method over the Session object ---
      dispatch('leaveRoom', data)
      commit('LEAVE_SESSION')
		},

    getToken ({ dispatch }, sessionId) {
			return dispatch('createSession', sessionId).then(sessionId => dispatch('createToken', sessionId));
		},

		// See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-openviduapisessions
		createSession (context, sessionId) {
			return new Promise((resolve, reject) => {
				axios
					.post(`${process.env.VUE_APP_OPENVIDU_URL}/openvidu/api/sessions`, JSON.stringify({
						customSessionId: sessionId,

					}), {
						auth: {
							username: 'OPENVIDUAPP',
							password: process.env.VUE_APP_OPENVIDU_SECRET,
						},
					})
					.then(response => response.data)
					.then(data => resolve(data.id))
					.catch(error => {
            console.log('error', error)
						if (error.response.status === 409) {
							resolve(sessionId);
						} else {
							console.warn(`No connection to OpenVidu Server. This may be a certificate error at ${process.env.VUE_APP_OPENVIDU_URL}`);
							if (window.confirm(`No connection to OpenVidu Server. This may be a certificate error at ${process.env.VUE_APP_OPENVIDU_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${process.env.VUE_APP_OPENVIDU_URL}"`)) {
								location.assign(`${process.env.VUE_APP_OPENVIDU_URL}/accept-certificate`);
							}
							reject(error.response);
						}
					});
			});
		},

		// See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-openviduapisessionsltsession_idgtconnection
		createToken (context, sessionId) {
			return new Promise((resolve, reject) => {
				axios
					.post(`${process.env.VUE_APP_OPENVIDU_URL}/openvidu/api/sessions/${sessionId}/connection`, { 
            "type":"WEBRTC",
            "kurentoOptions" : {
              "allowedFilters": ["GStreamerFilter", "FaceOverlayFilter"]
            }
          },
          {
						auth: {
							username: 'OPENVIDUAPP',
							password: process.env.VUE_APP_OPENVIDU_SECRET,
						},
					})
					.then(response => response.data)
					.then(data => resolve(data.token))
					.catch(error => reject(error.response));
			});
		},
    switchGameMode ({ state }, mode) {
      state.session.signal({
        data: mode,
        to: [],
        type: 'game-mode'
      })
    },
    changeGameMode ({ commit }, mode) {
      const data = { gameMode: mode }
      commit('SET_GAME_MODE', data)
    },
    enterRoom (context, sessionId) {
      axios({
        method: 'POST', 
        url: `${process.env.VUE_APP_API_URL}/meeting/enter/${sessionId}`,
        headers: { Authorization: `Bearer ${localStorage.getItem('jwt')}`}
      })

    },
    leaveRoom (context, data) {
      axios({
        method: 'POST', 
        url: `${process.env.VUE_APP_API_URL}/meeting/leave/${data.sessionId}`,
        data: data.data,
        headers: { Authorization: `Bearer ${localStorage.getItem('jwt')}`}
      })

    }
  },
  getters: {

  },
  modules: {
  }
}

export default openviduStore