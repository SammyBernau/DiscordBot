//package org.gamerbot.example;
//
//import io.ruin.services.exylediscord.commands.handlers.ButtonHandler;
//import io.ruin.services.exylediscord.commands.handlers.support.HiScoresPageSupport;
//import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
//
//public class PPageHandler implements ButtonHandler, HiScoresPageSupport {
//
//    @Override
//    public void handleButton(ButtonInteractionEvent event) {
//        String id = event.getComponentId();
//        String[] args = id.split(":");
//        int page = Integer.parseInt(args[1]);
//        String skillNameOption = args[3];
//        String skillNameColumn = args[2];
//        int xpRateOption = Integer.parseInt(args[4]);
//        replyWithHiScoresEmbed(skillNameColumn, skillNameOption, xpRateOption, page - 1, event);
//    }
//}
