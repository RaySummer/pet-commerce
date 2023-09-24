INSERT into "public"."configuration"("created_time", "created_by", "key", "value", "description", "platform")
values (now(), 'Ray', 'OPEN_AI',
        ' {"apiKey":"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","organizationId":"org-B2fgWNzZ4RdnzOv0Sc45eF0g","openAIUrl":"https://api.openai.com/v1/completions"}',
        'configuration of open ai', 'PORTAL');
