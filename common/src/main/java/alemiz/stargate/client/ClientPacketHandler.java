/**
 * Copyright 2020 WaterdogTEAM
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package alemiz.stargate.client;

import alemiz.stargate.protocol.DisconnectPacket;
import alemiz.stargate.protocol.PingPacket;
import alemiz.stargate.protocol.PongPacket;
import alemiz.stargate.protocol.ReconnectPacket;
import alemiz.stargate.session.SessionHandler;

public class ClientPacketHandler extends SessionHandler<ClientSession> {

    public ClientPacketHandler(ClientSession session){
        super(session);
    }

    @Override
    public boolean handleDisconnect(DisconnectPacket packet) {
        this.session.onDisconnect(packet.getReason());
        return true;
    }

    @Override
    public boolean handlePing(PingPacket packet) {
        PongPacket pongPacket = new PongPacket();
        pongPacket.setPingTime(packet.getPingTime());
        this.session.forcePacket(pongPacket);
        return true;
    }

    @Override
    public boolean handlePong(PongPacket packet) {
        this.session.onPongReceive(packet);
        return true;
    }

    @Override
    public boolean handleReconnect(ReconnectPacket packet) {
        this.session.reconnect(packet.getReason(), false);
        return true;
    }
}
