package com.jcsanchez.helloworld;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lexruntime.model.PostTextResult;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.jcsanchez.helloworld.model.*;

public class Main implements RequestHandler<LexRequest, LexResponse>{
    static LambdaLogger logger;
    static Set<String> flowerTypes = new HashSet<>();

    {
        flowerTypes.add("lilies");
        flowerTypes.add("roses");
        flowerTypes.add("tulips");
    }

    @Override
    public LexResponse handleRequest(LexRequest request, Context context) {
        logger = context.getLogger();

        logger.log(String.format("event.bot.name=%s\n", request.getBot().getName()));

        try {
            return dispatch(request);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private LexResponse dispatch(LexRequest request) throws Exception {
        String intentName = request.getCurrentIntent().getName();
        String userId = request.getUserId();

        logger.log(String.format("dispatch userId=%s, intentName=%s\n", userId, intentName));

        if (intentName.equals("OrderFlowers")) {
            return orderFlowers(request);
        }

        throw new Exception("Intent with name " + intentName + " not supported");
    }

    private LexResponse orderFlowers(LexRequest request) {
        Map<String, String> slots = request.getCurrentIntent().getSlots();

        String flowerType = slots.get("FlowerType");
        String date = slots.get("PickupDate");
        String pickupTime = slots.get("PickupTime");
        String source = request.getInvocationSource();

        if (source.equals("DialogCodeHook")) {
            Map<String, String> responseSlots = new HashMap<>();
            responseSlots.putAll(slots);

            Map<String, String> validationResults = validateOrderFlowers(flowerType, date, pickupTime);

            if (validationResults.get("isValid").equals(Boolean.toString(false))) {
                responseSlots.put(validationResults.get("violatedSlot"), null);
                return elicitSlot(request.getSessionAttributes(),
                        request.getCurrentIntent().getName(),
                        responseSlots,
                        validationResults.get("violatedSlot"),
                        validationResults.get("message"));
            }

            Map<String, String> responseAttributes = new HashMap<>();

            if (request.getSessionAttributes() != null) {
                responseAttributes.putAll(request.getSessionAttributes());
            }

            if (flowerType != null) {
                responseAttributes.put("Price", Integer.toString(flowerType.length() * 5));
            }

            return delegate(responseAttributes, responseSlots);
        }

        return close(request.getSessionAttributes(), "Fulfilled",
                new Message().withContentType(ContentType.PlainText)
                             .withContent(String.format("Thanks, your order for %s has been placed and will be ready for pickup by %s on %s",
                                     flowerType, pickupTime, date)));
    }

    private Map<String, String> validateOrderFlowers(String flowerTypeString, String dateString, String pickupTimeString) {
        logger.log("Validating Order\n");
        if (flowerTypeString != null && !flowerTypes.contains(flowerTypeString.toLowerCase())) {
            return buildValidationResult(false, "FlowerType",
                    String.format("We do not have %s, would you like a different type of flower?  \n" +
                            "Our most popular flowers are roses", flowerTypeString));
        }

        if (dateString != null) {
            try {
                if (LocalDate.parse(dateString).compareTo(LocalDate.now()) <= 0) {
                    return buildValidationResult(false, "PickupDate",
                            "You can pick up the flowers from tomorrow onwards.  What day would you like to pick them up?");
                }
            } catch (DateTimeParseException e) {
                return buildValidationResult(false, "PickupDate",
                        "I did not understand that, what date would you like to pick the flowers up?");
            }
        }

        if (pickupTimeString != null) {
            try {
                int pickupHour = LocalTime.parse(pickupTimeString).getHour();
                if (pickupHour < 10 || pickupHour > 16) {
                    return buildValidationResult(false, "PickupTime",
                            "Our business hours are from ten a m. to five p m. Can you specify a time during this range?");
                }
            } catch (DateTimeParseException e) {
                buildValidationResult(false, "PickupTime", null);
            }
        }

        return buildValidationResult(true, null, null);
    }

    private Map<String, String> buildValidationResult(boolean isValid, String violatedSlot, String messageContent) {
        Map<String, String> result = new HashMap<>();

        result.put("isValid", Boolean.toString(isValid));
        result.put("violatedSlot", violatedSlot);
        result.put("message", messageContent);

        return result;
    }

    private LexResponse elicitSlot(Map<String, String> sessionAttributes,
                                      String intentName,
                                      Map<String, String> slots,
                                      String slotToElicit,
                                      String message) {

        DialogAction dialogAction = new DialogAction()
                .withType(DialogActionType.ElicitSlot)
                .withIntentName(intentName)
                .withSlots(slots)
                .withSlotToElicit(slotToElicit)
                .withMessage(new Message()
                                .withContentType(ContentType.PlainText)
                                .withContent(message));

        return new LexResponse()
                .withSessionAttributes(sessionAttributes)
                .withDialogAction(dialogAction);
    }

    private LexResponse delegate(Map<String, String> sessionAttributes, Map<String, String> slots) {
        DialogAction dialogAction = new DialogAction()
                .withType(DialogActionType.Delegate)
                .withSlots(slots);

        return new LexResponse()
                .withSessionAttributes(sessionAttributes)
                .withDialogAction(dialogAction);
    }

    private LexResponse close(Map<String, String> sessionAttributes, String fulfillmentState, Message message) {
        DialogAction dialogAction = new DialogAction()
                .withType(DialogActionType.Close)
                .withFullfillmentState(FulfillmentState.fromValue(fulfillmentState))
                .withMessage(message);

        return new LexResponse()
                .withSessionAttributes(sessionAttributes)
                .withDialogAction(dialogAction);
    }
}
