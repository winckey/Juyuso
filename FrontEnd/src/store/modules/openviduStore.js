/* eslint-disable no-undef */
import { OpenVidu } from 'openvidu-browser'
import axios from 'axios'
axios.defaults.headers.post['Content-Type'] = 'application/json';

const openviduStore = {
  namespaced: true,
  state: {
    OV: undefined,
    session: undefined,
    mainStreamManager: undefined,
    publisher: undefined,
    subscribers: [],
    messages: [],
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
    LEAVE_SESSION(state) {
      if (state.session) state.session.disconnect();
			state.session = undefined;
			state.mainStreamManager = undefined;
			state.publisher = undefined;
			state.subscribers = [];
      state.messages = [];
			state.OV = undefined;
    }
  },
  actions: {
    joinSession: function ({ dispatch, commit }, roomInfo) {
      let data = {
        OV: null,
        session: null,
        mainStreamManager: null,
        publisher: null,
        subscribers: [],
        messages: [],
      }
      const sessionId = roomInfo.sessionId
      data.OV = new OpenVidu();
			// --- Init a session ---
			data.session = data.OV.initSession();

			// --- Specify the actions when events take place in the session ---
			// On every new Stream received...
			data.session.on('streamCreated', ({ stream }) => {
				const subscriber = data.session.subscribe(stream);
				data.subscribers.push(subscriber);
			});
			// On every Stream destroyed...
			data.session.on('streamDestroyed', ({ stream }) => {
        const index = data.subscribers.indexOf(stream.streamManager, 0);
				if (index >= 0) {
          data.subscribers.splice(index, 1);
				}
			});

      data.session.on('signal:my-chat', (event) => {
        data.messages.push(event)
        console.log(event.data); // Message
        console.log(event.from); // Connection object of the sender
        console.log(event.type); // The type of message ("my-chat")
      });

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
              let publisher = data.OV.initPublisher(undefined, {
                audioSource: undefined, // The source of audio. If undefined default microphone
                videoSource: undefined, // The source of video. If undefined default webcam
                publishAudio: true,  	// Whether you want to start publishing with your audio unmuted or not
                publishVideo: true,  	// Whether you want to start publishing with your video enabled or not
                resolution: '640x480',  // The resolution of your video
                frameRate: 30,			// The frame rate of your video
                insertMode: 'APPEND',	// How the video is inserted in the target element 'video-container'
                mirror: false       	// Whether to mirror your local video or not
              });
              data.mainStreamManager = publisher;
              data.publisher = publisher;
              // --- Publish your stream ---
              data.session.publish(publisher);
              // dispatch('enterRoom', sessionId)
              commit('SET_SESSION_INFO', data)
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
              let publisher = data.OV.initPublisher(undefined, {
                audioSource: undefined, // The source of audio. If undefined default microphone
                videoSource: undefined, // The source of video. If undefined default webcam
                publishAudio: true,  	// Whether you want to start publishing with your audio unmuted or not
                publishVideo: true,  	// Whether you want to start publishing with your video enabled or not
                resolution: '640x480',  // The resolution of your video
                frameRate: 30,			// The frame rate of your video
                insertMode: 'APPEND',	// How the video is inserted in the target element 'video-container'
                mirror: false       	// Whether to mirror your local video or not
              });
              data.mainStreamManager = publisher;
              data.publisher = publisher;
              // --- Publish your stream ---
              data.session.publish(publisher);
              // dispatch('enterRoom', sessionId)
              commit('SET_SESSION_INFO', data)
            })
            .catch(error => {
              console.log('There was an error connecting to the session:', error.code, error.message);
            });
        });
      }
    },

    leaveSession ({ dispatch, commit }, sessionId) {
			// --- Leave the session by calling 'disconnect' method over the Session object ---
      dispatch('leaveRoom', sessionId)
      commit('LEAVE_SESSION')
		},

    getToken ({ dispatch }, sessionId) {
			return dispatch('createSession', sessionId).then(sessionId => dispatch('createToken', sessionId));
		},

		// See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-openviduapisessions
		createSession (context, sessionId) {
      console.log('createSession:', sessionId)
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
						if (error.response.status === 409) {
							resolve(sessionId);
						} else {
							console.warn(`No connection to OpenVidu Server. This may be a certificate error at ${process.env.VUE_APP_OPENVIDU_URL}`);
							if (window.confirm(`No connection to OpenVidu Server. This may be a certificate error at ${process.env.VUE_APP_OPENVIDU_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`)) {
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
					.post(`${process.env.VUE_APP_OPENVIDU_URL}/openvidu/api/sessions/${sessionId}/connection`, {}, {
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

    enterRoom (context, sessionId) {
      console.log(sessionId)
      // axios({
      //   method: 'POST', 
      //   url: `${process.env.VUE_APP_API_URL}/meeting/enter/${sessionId}`
      // })

    },
    leaveRoom (context, sessionId) {
      console.log(sessionId)
      // axios({
      //   method: 'POST', 
      //   url: `${process.env.VUE_APP_API_URL}/meeting/leave/${sessionId}`
      // })

    }
  },
  getters: {

  },
  modules: {
  }
}

export default openviduStore